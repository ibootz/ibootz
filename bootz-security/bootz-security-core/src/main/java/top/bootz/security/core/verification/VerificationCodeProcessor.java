/**
 * 
 */
package top.bootz.security.core.verification;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:25:11
 */
public interface VerificationCodeProcessor {

    String VERIFICATION_CODE_PROCESSOR_SUFFIX = "CodeProcessor";
    
	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}
