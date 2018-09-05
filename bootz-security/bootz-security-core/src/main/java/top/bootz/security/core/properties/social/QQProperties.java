package top.bootz.security.core.properties.social;

import lombok.Data;

/**
 * QQ登录配置项
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月5日 下午8:59:35
 */

@Data
public class QQProperties {

    private String appId;

    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     * <p>
     * /auth/qq中的qq即由这里的配置决定
     */
    private String providerId = "qq";

}
