/**
 * 
 */
package top.bootz.security.core.properties.social;

import lombok.Data;

/**
 * 微信登录配置项
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月5日 下午8:59:24
 */
@Data
public class WeixinProperties {

    private String appId;

    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";

}
