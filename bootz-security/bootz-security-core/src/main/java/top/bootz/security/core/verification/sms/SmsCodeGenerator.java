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
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午9:25:56
 */
@Component
public class SmsCodeGenerator implements VerificationCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public VerificationCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmsCode().getLength());
        return new VerificationCode(code, securityProperties.getCode().getSmsCode().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
