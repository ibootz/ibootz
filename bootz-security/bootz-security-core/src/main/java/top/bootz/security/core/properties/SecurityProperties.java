package top.bootz.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import top.bootz.security.core.properties.code.VerificationCodeProperties;
import top.bootz.security.core.properties.cors.CorsProperties;
import top.bootz.security.core.properties.oauth2.OAuth2Properties;
import top.bootz.security.core.properties.session.SessionProperties;
import top.bootz.security.core.properties.social.SocialProperties;

/**
 * 安全组件相关自定已配置，该配置交由使用我们组件的应用自主配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月19日 下午3:51:01
 */

@Data
@ConfigurationProperties(prefix = "bootz.security")
public class SecurityProperties {

    /**
     * 采用session保存状态的系统相关配置
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 验证码相关自定义配置
     */
    private VerificationCodeProperties code = new VerificationCodeProperties();

    /**
     * 社交登录配置
     */
    private SocialProperties social = new SocialProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();

    private CorsProperties cors = new CorsProperties();

}
