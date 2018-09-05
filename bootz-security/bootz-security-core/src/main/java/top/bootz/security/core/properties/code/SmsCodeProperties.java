package top.bootz.security.core.properties.code;

import lombok.Data;

/**
 * 短信验证码相关配置
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午9:44:32
 */
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 过期时间，单位：秒
     */
    private int expireIn = 60;

    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;

}
