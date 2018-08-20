package top.bootz.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 安全组件相关自定已配置，该配置交由使用我们组件的应用自主配置
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月19日 下午3:51:01
 */

@Data
@ConfigurationProperties(prefix = "bootz.security")
public class SecurityProperties {

	private SessionProperties session = new SessionProperties();

}
