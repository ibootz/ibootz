package top.bootz.security.core.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import top.bootz.security.core.social.qq.api.QQ;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月3日 下午10:03:45
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId 提供商唯一Id
     * @param appId      我们在提供商里注册的应用的唯一标识
     * @param appSecret  应用秘钥
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
