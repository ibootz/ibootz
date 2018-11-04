package top.bootz.security.token.verification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.verification.VerificationCode;
import top.bootz.security.core.verification.VerificationCodeRepository;
import top.bootz.security.core.verification.VerificationCodeType;

/**
 * 验证码存取器
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年11月4日 下午4:59:13
 */
@Component
public class TokenVerificationCodeRepository implements VerificationCodeRepository {

    private static final String JSESSIONID = "JSESSIONID";
    
    private Map<String, VerificationCode> cacheMap = new ConcurrentHashMap<>();

    @Override
    public void save(ServletWebRequest request, VerificationCode code, VerificationCodeType verificationCodeType) {
        String sessionId = request.getParameter(JSESSIONID);
        this.cacheMap.put(sessionId + verificationCodeType.name(), code);
    }

    @Override
    public VerificationCode get(ServletWebRequest request, VerificationCodeType verificationCodeType) {
        String sessionId = request.getParameter(JSESSIONID);
        return this.cacheMap.get(sessionId + verificationCodeType.name());
    }

    @Override
    public void remove(ServletWebRequest request, VerificationCodeType verificationCodeType) {
        String sessionId = request.getParameter(JSESSIONID);
        this.cacheMap.remove(sessionId + verificationCodeType.name());
    }

}
