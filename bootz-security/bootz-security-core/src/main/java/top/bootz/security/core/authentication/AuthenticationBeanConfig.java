package top.bootz.security.core.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 认证相关的扩展点配置。
 * <p>
 * 配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全 模块默认的配置。
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月2日 下午4:47:44
 */
@Configuration
public class AuthenticationBeanConfig {

    /**
     * 默认密码处理器
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 默认UserDetailService
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

}
