package top.bootz.security.core.verification.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信验证码发送器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午9:25:22
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机" + mobile + "发送短信验证码" + code);
    }

}
