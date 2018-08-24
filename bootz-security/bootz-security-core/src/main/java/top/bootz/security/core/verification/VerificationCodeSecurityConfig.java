/**
 * 
 */
package top.bootz.security.core.verification;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 * 校验码相关安全配置
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:23:03
 */
@Component
public class VerificationCodeSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private Filter verificationCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verificationCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

}
