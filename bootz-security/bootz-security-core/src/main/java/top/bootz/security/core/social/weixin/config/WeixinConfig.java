package top.bootz.security.core.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.social.WeixinProperties;
import top.bootz.security.core.social.weixin.connect.WeixinConnectionFactory;

/**
 * 微信登录配置
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年10月16日 下午8:37:25
 */

@Configuration
@ConditionalOnProperty(prefix = "bootz.security.social.weixin", name = "app-id")
public class WeixinConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    private ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinProperties = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(),
                weixinProperties.getAppSecret());
    }

    /**
     * 做到处理注册逻辑的时候发现的一个bug：登录完成后，数据库没有数据，但是再次登录却不用注册了。
     * <p>
     * 结果果然发现这里父类返回的InMemoryUsersConnectionRepository覆盖了SocialConfig中配置的jdbcConnectionRepository
     * <p>
     * 这里需要返回null，否则会返回InMemoryUsersConnectionRepository
     * 
     * @param connectionFactoryLocator
     * @return
     * @datetime 2018年9月5日 下午8:32:21
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }

}
