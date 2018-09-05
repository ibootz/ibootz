package top.bootz.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.social.QQProperties;
import top.bootz.security.core.social.qq.connect.QQConnectionFactory;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月3日 下午11:14:14
 */

@Component
//@ConditionalOnProperty(prefix = "bootz.security.socail", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    private ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(),
                qqProperties.getAppSecret());
    }

    /**
     * 做到处理注册逻辑的时候发现的一个bug：登录完成后，数据库没有数据，但是再次登录却不用注册了。
     * <p>
     * 结果果然发现这里父类的内存ConnectionRepository覆盖了SocialConfig中配置的jdbcConnectionRepository
     * <p>
     * 这里需要返回null，否则会返回内存的 ConnectionRepository
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
