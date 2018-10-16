package top.bootz.security.core.properties.social;

import lombok.Data;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月3日 下午10:58:24
 */

@Data
public class SocialProperties {

    /**
     * 社交登录功能拦截的url，各个子项目可以自定义该参数
     */
    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

}
