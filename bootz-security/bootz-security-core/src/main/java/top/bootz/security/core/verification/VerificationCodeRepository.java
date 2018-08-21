/**
 * 
 */
package top.bootz.security.core.verification;

import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.verification.captcha.Captcha;

/**
 * 校验码存取器
 * 
 * @author zhailiang
 *
 */
public interface VerificationCodeRepository {

	/**
	 * 保存验证码
	 * 
	 * @param request
	 * @param code
	 * @param validateCodeType
	 */
	void save(ServletWebRequest request, VerificationCode code, VerificationCodeType validateCodeType);

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	Captcha get(ServletWebRequest request, VerificationCodeType validateCodeType);

	/**
	 * 移除验证码
	 * 
	 * @param request
	 * @param codeType
	 */
	void remove(ServletWebRequest request, VerificationCodeType codeType);

}
