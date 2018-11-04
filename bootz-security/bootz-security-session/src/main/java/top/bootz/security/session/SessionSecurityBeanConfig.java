/**
 * 
 */
package top.bootz.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.session.logout.CustomLogoutSuccessHandler;
import top.bootz.security.session.strategy.CustomExpiredSessionStrategy;
import top.bootz.security.session.strategy.CustomInvalidSessionStrategy;

/**
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全 模块默认的配置。
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年10月27日 上午8:32:55
 */
@Configuration
public class SessionSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomExpiredSessionStrategy(securityProperties);
    }

    /**
     * 退出时的处理策略配置
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler(securityProperties.getSession().getSignOutUrl());
    }

}
