package top.bootz.security.core.social;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.util.CollectionUtils;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.social.support.CustomSpringSocialConfigurer;
import top.bootz.security.core.social.support.SocialAuthenticationFilterPostProcessor;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月3日 下午10:09:53
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private List<ConnectInterceptor<?>> connectInterceptors;

    @Autowired(required = false)
    private List<DisconnectInterceptor<?>> disconnectInterceptors;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // Encryptors.noOpText() 第三个参数用来对保存在UserConnection表里面的accessToken、Secret、RefreshToken进行加密解谜操作
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());

        // 后台系统为第三方登录过来的用户创建用户数据，不再需要跳转到注册绑定页面去绑定
        if (connectionSignUp != null) {
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }

        return usersConnectionRepository;
    }

    /**
     * 提取系统认证用户的实体的唯一标识，对应Spring Social绑定表UserConnection表中的userId
     * 
     * @return
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月24日 上午12:23:23
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new IdentityUserIdSource();
    }

    /**
     * 该工具类处理两件事情：
     * <p>
     * 1.用户使用社交账号登录之后跳转回我们的注册/绑定页面的时候，怎么拿到spring social返回来的信息
     * <p>
     * 2.怎么将我们系统用户表中的userId提供给spring socail，让其保存在UserConnection表中。
     * 
     * @param factoryLocator
     * @return
     * @datetime 2018年9月11日 下午9:16:28
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator) {
        return new ProviderSignInUtils(factoryLocator, getUsersConnectionRepository(factoryLocator));
    }

    @Bean
    public SpringSocialConfigurer bootzSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        CustomSpringSocialConfigurer configurer = new CustomSpringSocialConfigurer(filterProcessesUrl);
        // 在数据库中找不到qqopenid对应的userId时，跳转到自定义注册页面，提醒用户注册或者绑定一下账号
        configurer.signupUrl(securityProperties.getSession().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 用来处理用户绑定/解绑相关逻辑
     * 
     * @param factoryLocator
     * @param repository
     * @return
     * @datetime 2018年9月23日 下午11:49:57
     */
    @Bean
    @ConditionalOnMissingBean(ConnectController.class)
    public ConnectController connectController(ConnectionFactoryLocator factoryLocator,
            ConnectionRepository repository) {
        ConnectController controller = new ConnectController(factoryLocator, repository);
        if (!CollectionUtils.isEmpty(this.connectInterceptors)) {
            controller.setConnectInterceptors(this.connectInterceptors);
        }
        if (!CollectionUtils.isEmpty(this.disconnectInterceptors)) {
            controller.setDisconnectInterceptors(this.disconnectInterceptors);
        }
        return controller;
    }

}
