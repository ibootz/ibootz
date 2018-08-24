package top.bootz.security.core.verification.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.SecurityConstants;
import top.bootz.security.core.verification.AbstractVerificationCodeProcessor;
import top.bootz.security.core.verification.VerificationCode;

/**
 * 短信验证码处理器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午9:26:13
 */
@Component
public class SmsCodeProcessor extends AbstractVerificationCodeProcessor<VerificationCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, VerificationCode VerificationCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, VerificationCode.getCode());
    }

}
