package top.bootz.security.core.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import top.bootz.security.core.SecurityConstants;

/**
 * 表单登录配置
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月2日 下午4:46:06
 */
@Component
public class FormAuthenticationConfig {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.formLogin()
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL) // 指定登录页面或请求url
            .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM) // 如果指定了自定义loginpage，那么该配置必不可少，否则默认和loginPage一样
            .successHandler(authenticationSuccessHandler) // 设置登录认证成功之后的处理类
            .failureHandler(authenticationFailureHandler); // 设置登录认证失败之后的处理类
        // @formatter:on
    }

}
