package top.bootz.security.core.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.verification.captcha.CaptchaGenerator;
import top.bootz.security.core.verification.sms.DefaultSmsCodeSender;
import top.bootz.security.core.verification.sms.SmsCodeSender;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全模块默认的配置。
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月22日 上午1:26:40
 */
@Configuration
public class VerificationCodeBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * 图片验证码图片生成器
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(name = "captchaGenerator")
	public CaptchaGenerator captchaGenerator() {
		CaptchaGenerator codeGenerator = new CaptchaGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}

	/**
	 * 短信验证码发送器
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}
