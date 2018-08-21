/**
 * 
 */
package top.bootz.security.core.verification;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校验码处理器管理器
 * 
 * @author zhailiang
 *
 */
@Component
public class VerificationCodeProcessorHolder {

	@Autowired
	private Map<String, VerificationCodeProcessor> validateCodeProcessors;

	/**
	 * @param type
	 * @return
	 */
	public VerificationCodeProcessor findVerificationCodeProcessor(VerificationCodeType type) {
		return findVerificationCodeProcessor(type.toString().toLowerCase());
	}

	/**
	 * @param type
	 * @return
	 */
	public VerificationCodeProcessor findVerificationCodeProcessor(String type) {
		String name = type.toLowerCase() + VerificationCodeProcessor.class.getSimpleName();
		VerificationCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new VerificationCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
