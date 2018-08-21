package top.bootz.security.core.verification;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 抽象的图片验证码处理器
 * 
 * @author zhailiang
 *
 */
public abstract class AbstractVerificationCodeProcessor<C extends VerificationCode>
		implements VerificationCodeProcessor {

	/**
	 * 收集系统中所有的 {@link VerificationCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, VerificationCodeGenerator> validateCodeGenerators;

	@Autowired
	private VerificationCodeRepository validateCodeRepository;

	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		String type = getVerificationCodeType(request).toString().toLowerCase();
		String generatorName = type + VerificationCodeGenerator.class.getSimpleName();
		VerificationCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new VerificationCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		VerificationCode code = new VerificationCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getVerificationCodeType(request));
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private VerificationCodeType getVerificationCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return VerificationCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		VerificationCodeType codeType = getVerificationCodeType(request);

		C codeInSession = (C) validateCodeRepository.get(request, codeType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					codeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new VerificationCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new VerificationCodeException(codeType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new VerificationCodeException(codeType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(request, codeType);
			throw new VerificationCodeException(codeType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new VerificationCodeException(codeType + "验证码不匹配");
		}

		validateCodeRepository.remove(request, codeType);

	}

}
