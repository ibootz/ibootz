package top.bootz.security.core.properties;

import lombok.Data;

/**
 * 验证码配置
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午10:54:29
 */
@Data
public class VerificationCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties imageCode = new ImageCodeProperties();

    /**
     * 短信验证码配置
     */
    private SmsCodeProperties smsCode = new SmsCodeProperties();

}
