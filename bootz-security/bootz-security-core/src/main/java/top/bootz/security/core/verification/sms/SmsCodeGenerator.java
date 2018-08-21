package top.bootz.security.core.verification.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.verification.VerificationCode;
import top.bootz.security.core.verification.VerificationCodeGenerator;

/**
 * 短信验证码生成器
 * 
 * @author zhailiang
 *
 */
@Component("smsVerificationCodeGenerator")
public class SmsCodeGenerator implements VerificationCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.Verification.code.VerificationCodeGenerator#generate(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public VerificationCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new VerificationCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	

}
