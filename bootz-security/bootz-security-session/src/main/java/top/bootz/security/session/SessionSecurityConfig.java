package top.bootz.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import top.bootz.security.core.authentication.FormAuthenticationConfig;
import top.bootz.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import top.bootz.security.core.authorize.AuthorizeConfigManager;
import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.verification.VerificationCodeSecurityConfig;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月18日 下午7:56:17
 */

@Configuration
public class SessionSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private VerificationCodeSecurityConfig verificationCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer bootzSocialSecurityConfig;
    
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        formAuthenticationConfig.configure(http); // 表单认证
        
        http.apply(verificationCodeSecurityConfig) // 校验码相关安全配置(图形验证码和短信验证码)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig) // 短信验证码登录配置
                .and()
            .apply(bootzSocialSecurityConfig) // 第三方社交网站登录配置
                .and()
            .rememberMe() // 记住我
                .key(securityProperties.getSession().getRememberMeKey())
                .tokenValiditySeconds(securityProperties.getSession().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                .and()
                .and()
            .csrf().disable(); // 禁用csrf检查
        
        authorizeConfigManager.config(http.authorizeRequests()); // 授权配置
        // @formatter:on
    }

}
