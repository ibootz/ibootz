package top.bootz.security.web.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import top.bootz.security.core.authorize.AuthorizeConfigProvider;

/**
 * 浏览器环境默认的授权配置，对常见的静态资源，如js,css，图片等不验证身份
 * 
 * @author zhailiang
 */
@Component
public class CustomAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        // @formatter:off
	    config.antMatchers(HttpMethod.POST, "/user/socail/bind").permitAll(); // 社交用户信息绑定的路径授权 
	    // @formatter:on

        return false;
    }

}
