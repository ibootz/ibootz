package top.bootz.security.session.verification;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.verification.VerificationCode;
import top.bootz.security.core.verification.VerificationCodeRepository;
import top.bootz.security.core.verification.VerificationCodeType;

/**
 * 基于session的验证码存取器
 * 
 * @author zhailiang
 *
 */
@Component
public class SessionVerificationCodeRepository implements VerificationCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    @Override
    public void save(ServletWebRequest request, VerificationCode code, VerificationCodeType verificationCodeType) {
        request.setAttribute(getSessionKey(verificationCodeType), code, RequestAttributes.SCOPE_SESSION);
    }

    @Override
    public VerificationCode get(ServletWebRequest request, VerificationCodeType verificationCodeType) {
        return (VerificationCode) request.getAttribute(getSessionKey(verificationCodeType),
                ServletWebRequest.SCOPE_SESSION);
    }

    @Override
    public void remove(ServletWebRequest request, VerificationCodeType codeType) {
        request.removeAttribute(getSessionKey(codeType), ServletWebRequest.SCOPE_SESSION);
    }

    /**
     * 构建验证码放入session时的key
     * 
     * @param request
     * @return
     */
    private String getSessionKey(VerificationCodeType verificationCodeType) {
        return SESSION_KEY_PREFIX + verificationCodeType.toString().toUpperCase();
    }

}
