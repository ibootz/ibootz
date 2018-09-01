package top.bootz.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import top.bootz.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.verification.VerificationCodeFilter;
import top.bootz.security.session.authentication.SessionAuthenticationFailureHandler;
import top.bootz.security.session.authentication.SessionAuthenticationSuccessHandler;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月18日 下午7:56:17
 */

@Configuration
public class SessionSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SessionAuthenticationSuccessHandler sessionAuthenticationSuccessHandler;

    @Autowired
    private SessionAuthenticationFailureHandler sessionAuthenticationFailureHandler;

    @Autowired
    private VerificationCodeFilter verificationCodeFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin() // 开启表单认证
                .loginPage("/authentication/require") // 指定登录页面或请求url
                .loginProcessingUrl("/authentication/form") // 如果指定了自定义loginpage，那么该配置必不可少，否则默认和loginPage一样
                .successHandler(sessionAuthenticationSuccessHandler) // 设置登录认证成功之后的处理类
                .failureHandler(sessionAuthenticationFailureHandler) // 设置登录认证失败之后的处理类
            .and()
                .rememberMe() // 记住我
                    .key(securityProperties.getSession().getRememberMeKey())
                    .tokenValiditySeconds(securityProperties.getSession().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
            .and()
                .csrf().disable() // 禁用csrf检查
            .authorizeRequests() // 授权配置
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // options请求不认证
                .antMatchers("/favicon.ico", "/index.html", "/html/login.html", 
                        "/css/**", "/js/**", "/images/**", "/plugins/**", "/fonts/**").permitAll() // 静态资源不认证
                .antMatchers("/", "/authentication/require", "/authentication/form", "/verification/**",
                		securityProperties.getSession().getLoginPage()).permitAll() // 认证url本身不认证
                .anyRequest().authenticated(); // 其他任何请求都需要身份认证
        // @formatter:on
    }

}
