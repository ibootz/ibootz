package top.bootz.security.session.authorize;

import org.springframework.core.annotation.Order;
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
@Order(Integer.MIN_VALUE)
public class SessionAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        // @formatter:off
	    config.antMatchers(HttpMethod.GET, 
	            "/**/*.js",
	            "/**/*.css",
	            "/**/*.jpg",
	            "/**/*.png",
	            "/**/*.gif",
	            "/**/*.ico").permitAll(); // 静态资源不认证
	    // @formatter:on

        return false;
    }

}
