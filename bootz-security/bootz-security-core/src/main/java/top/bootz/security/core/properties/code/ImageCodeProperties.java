/**
 * 
 */
package top.bootz.security.core.properties.code;

import lombok.Data;

/**
 * 图片验证码配置项
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午10:54:43
 */
@Data
public class ImageCodeProperties {

    /**
     * 图片宽
     */
    private int width = 80;

    /**
     * 图片高
     */
    private int height = 32;

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
