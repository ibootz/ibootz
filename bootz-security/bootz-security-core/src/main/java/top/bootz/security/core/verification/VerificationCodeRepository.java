/**
 * 
 */
package top.bootz.security.core.verification;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:23:11
 */
public interface VerificationCodeRepository {

    /**
     * 保存验证码
     * 
     * @param request
     * @param code
     * @param verificationCodeType
     */
    void save(ServletWebRequest request, VerificationCode code, VerificationCodeType verificationCodeType);

    /**
     * 获取验证码
     * 
     * @param request
     * @param verificationCodeType
     * @return
     */
    VerificationCode get(ServletWebRequest request, VerificationCodeType verificationCodeType);

    /**
     * 移除验证码
     * 
     * @param request
     * @param verificationCodeType
     */
    void remove(ServletWebRequest request, VerificationCodeType verificationCodeType);

}
