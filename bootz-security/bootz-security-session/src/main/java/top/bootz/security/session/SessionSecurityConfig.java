package top.bootz.security.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月18日 下午7:56:17
 */

@Configuration
public class SessionSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
            http
                .formLogin() // 开启表单认证
                    .loginPage("/authentication/require") // 指定登录页面
                    .loginProcessingUrl("/authentication/login") // 如果指定了自定义loginpage，那么该配置必不可少，否则默认和loginPage一样
                .and()
                    .csrf().disable() // 禁用csrf检查
                .authorizeRequests() // 授权配置
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/favicon.ico", "/index.html", "/html/**", 
                            "/css/**", "/js/**", "/images/**", "/plugins/**", "/fonts/**").permitAll() // 不需要登录即可访问的路径
                    .antMatchers("/authentication/require").permitAll()
                    .anyRequest().authenticated(); // 其他任何请求都需要身份认证
        // @formatter:on
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
