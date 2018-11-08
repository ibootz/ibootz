package top.bootz.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

import top.bootz.security.core.authentication.FormAuthenticationConfig;
import top.bootz.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import top.bootz.security.core.authorize.AuthorizeConfigManager;
import top.bootz.security.core.verification.VerificationCodeSecurityConfig;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月18日 下午7:56:17
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenSecurityConfig extends WebSecurityConfigurerAdapter {

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
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        formAuthenticationConfig.configure(http);
        
        http.apply(verificationCodeSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(bootzSocialSecurityConfig)
                .and()
//            .apply(openIdAuthenticationSecurityConfig)
//                .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        
        authorizeConfigManager.config(http.authorizeRequests());
        
        // @formatter:on
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
