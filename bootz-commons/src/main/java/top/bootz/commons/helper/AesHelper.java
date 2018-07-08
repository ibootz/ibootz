package top.bootz.commons.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import top.bootz.commons.exception.BaseRuntimeException;

public final class AesHelper {

    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_MODE = "AES/ECB/PKCS5Padding";

    private static final String ENCODE_RULES = "bjCUctiRaE1J1zJ0hEdwjrM82WKEmcm7SYhg=";

    private AesHelper() {
    }

    /**
     * @param byte[]
     *            source 要被加密的字节数组
     * @return byte[] 加密后的字节数组
     */
    public static byte[] encrypt(byte[] source) {
        byte[] result = null;
        try {
            SecretKey key = buildSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(source);
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to encrypt the content!", e);
        }
        return result;
    }

    /**
     * @param String
     *            source 要被加密的字符串
     * @return String 加密后经过base64编码的字符串
     */
    public static String encryptToBase64(String source) {
        return CodecHelper.toBase64(AesHelper.encrypt(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * @param byte[]
     *            encryptedContent 要被解密的字节数组
     * @return String 解密后的字符串
     */
    public static byte[] decrypt(byte[] encryptedContent) {
        byte[] result = null;
        try {
            SecretKey key = buildSecretKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(encryptedContent);
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to decrypt the encrypted content!", e);
        }
        return result;
    }

    /**
     * @param byte[]
     *            base64Encrypted 要被解密的经过base64编码过的内容
     * @return String 解密后的字符串
     */
    public static String decryptFromBase64(String base64Encrypted) {
        String result = StringHelper.EMPTY;
        try {
            result = new String(decrypt(CodecHelper.fromBase64(base64Encrypted)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to decrypt the encrypted base64 content!", e);
        }
        return result;
    }

    private static SecretKey buildSecretKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);

        // 根据ecnodeRules规则初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(ENCODE_RULES.getBytes(StandardCharsets.UTF_8));
        keygen.init(128, random);

        // 产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();

        // 获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();

        // 根据字节数组生成AES密钥
        return new SecretKeySpec(raw, ALGORITHM);
    }

    /*
     * public static void main(String[] args) { String source =
     * "中文English*&&$uawhuh2138761872ak97892389749(*&*(^@%^&%@!<>?<>:\"\"P{{P;,<，；’。；；】【132897897*（&……&！%@！）（*@！……%whukh21379287dawkuhku2h太多太多的中文需要测试1312983717dasjHLIJl213028^%$#（*781=2dawkuhadwadwduukw&*(*(7";
     * String after = encryptToBase64(source); String before =
     * decryptFromBase64(after); System.out.println(after);
     * System.out.println(source); System.out.println(before); }
     */
}