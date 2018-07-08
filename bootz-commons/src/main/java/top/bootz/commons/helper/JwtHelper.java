package top.bootz.commons.helper;

import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.SecurityConstants;

@Slf4j
public final class JwtHelper {

    /**
     * jwt签发者（本服务器）
     */
    public static final String CLAIMS_ISS = "iss";

    /**
     * token面向的用户（此处用来存储请求方的标识，PC/Android/IOS/H5）
     */
    public static final String CLAIMS_SUB = "sub";

    /**
     * 接受jwt的一方（这里存储的是对方的ip地址）
     */
    public static final String CLAIMS_AUD = "aud";

    /**
     * token过期时间，必须要大于签发时间
     */
    public static final String CLAIMS_EXP = "exp";

    /**
     * 刷新过期时间，在该时间段之内，可以使用旧token换取新token
     */
    public static final String CLAIMS_REF = "ref";

    /**
     * 定义一个时间，在该时间之前，此token都不可用
     */
    public static final String CLAIMS_NBF = "nbf";

    /**
     * token签发的时间
     */
    public static final String CLAIMS_IAT = "iat";

    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */
    public static final String CLAIMS_JTI = "jti";

    /**
     * userid
     */
    public static final String CLAIMS_USERID = "uid";

    /**
     * 访问用户的username
     */
    public static final String CLAIMS_USERNAME = "username";

    /**
     * 访问用户的角色字符串
     */
    public static final String CLAIMS_ROLES = "roles";

    /**
     * 客户端的设备类型
     */
    // public static final String CLAIMS_DEVICE = "device";

    private static RsaSigner signer;

    private static RsaVerifier verifier;

    private JwtHelper() {

    }

    static {
        Optional<Key> privOpt = RsaHelper.getPrivateKey();
        if (privOpt.isPresent()) {
            signer = new RsaSigner((RSAPrivateKey) privOpt.get());
        }

        Optional<Key> pubOpt = RsaHelper.getPublicKey();
        if (pubOpt.isPresent()) {
            verifier = new RsaVerifier((RSAPublicKey) pubOpt.get());
        }
    }

    /**
     * 同步加密生成JWT
     * 
     * @param payload：JSON字符串格式
     * @return
     */
    public static Optional<String> encode(String payload) {
        Jwt jwt = org.springframework.security.jwt.JwtHelper.encode(payload, signer);
        return Optional.of(jwt.getEncoded());
    }

    /**
     * 生成JWT
     * 
     * @param customPayloads：Map<String,
     *            String>
     * @param payload：JSON字符串格式
     * @return
     */
    public static Optional<String> encode(Map<String, Object> customPayloads) {
        Map<String, Object> payloads = buildNormalJwtPayloads();
        payloads.putAll(customPayloads);
        Jwt jwt = org.springframework.security.jwt.JwtHelper.encode(JsonHelper.toJSON(payloads), signer);
        return Optional.of(jwt.getEncoded());
    }

    /**
     * 填充通用的payload字段
     * 
     * @return
     */
    public static Map<String, Object> buildNormalJwtPayloads() {
        Map<String, Object> payloads = new HashMap<>();
        long createTime = Instant.now().toEpochMilli();
        payloads.put(CLAIMS_IAT, createTime); // 签发时间
        long expirationTime = createTime + SecurityConstants.JWT_EXPIRATION;
        payloads.put(CLAIMS_EXP, expirationTime); // 过期截止时间
        long refreshEndTime = createTime + SecurityConstants.JWT_REFRESH_END_TIME;
        payloads.put(CLAIMS_REF, refreshEndTime); // 刷新截止时间
        return payloads;
    }

    /**
     * 检测Token的第三部分签名是否合法（即没有被篡改过）
     */
    public static boolean verifyJwtToken(String jwtToken) {
        boolean isLegalToken = true;
        try {
            org.springframework.security.jwt.JwtHelper.decodeAndVerify(jwtToken, verifier);
        } catch (Exception e) {
            log.error("JWT - 签名验证失败！ [" + jwtToken + "]", e);
            isLegalToken = false;
        }
        return isLegalToken;
    }

    /**
     * 解密Jwt, 提取payload
     * 
     * @return
     */
    public static Optional<JSONObject> getPayload(String jwtToken) {
        try {
            Jwt jwt = org.springframework.security.jwt.JwtHelper.decodeAndVerify(jwtToken, verifier);
            return Optional.ofNullable(JsonHelper.getJSONObject(jwt.getClaims()));
        } catch (Exception e) {
            log.error("获取JWT的payload数据失败！ [" + e.getMessage() + "]", e);
        }
        return Optional.of(new JSONObject());
    }

    /**
     * 解密Jwt, 提取header
     * 
     * @return
     */
    public static Optional<JSONObject> getHeader(String jwtToken) {
        try {
            Jwt jwt = org.springframework.security.jwt.JwtHelper.decodeAndVerify(jwtToken, verifier);
            String header = new String(CodecHelper.fromUrlSafeBase64(jwt.getEncoded().split("\\.")[0]));
            return Optional.ofNullable(JsonHelper.getJSONObject(header));
        } catch (Exception e) {
            log.error("获取JWT的header数据失败！ [" + e.getMessage() + "]", e);
        }
        return Optional.of(new JSONObject());
    }

    public static Optional<String> getSignature(String jwtToken) {
        try {
            Jwt jwt = org.springframework.security.jwt.JwtHelper.decodeAndVerify(jwtToken, verifier);
            return Optional.of(new String(CodecHelper.fromUrlSafeBase64(jwt.getEncoded().split("\\.")[2])));
        } catch (Exception e) {
            log.error("获取JWT的signature数据失败！ [" + e.getMessage() + "]", e);
        }
        return Optional.of(StringHelper.EMPTY);
    }

}
