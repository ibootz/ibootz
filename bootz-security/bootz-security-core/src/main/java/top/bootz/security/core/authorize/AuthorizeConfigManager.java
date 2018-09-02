package top.bootz.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权信息管理器, 用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月2日 下午5:18:17
 */
public interface AuthorizeConfigManager {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
