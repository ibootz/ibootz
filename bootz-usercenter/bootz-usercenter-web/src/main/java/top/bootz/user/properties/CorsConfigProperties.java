package top.bootz.user.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Desc: 读取自定义Cors跨域配置文件
 *
 * @author John
 * 2018年6月10日 下午10:08:51
 */

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "custom.cors")
public class CorsConfigProperties {

    /**
     * 跨域访问，服务端要想获取客户端和设置客户端的Cookie，必须要设置具体的域地址，不能使用通配符*
     */
    public Boolean allowCredentials;

    /**
     * 设置为true，允许前端提交在ACCESS_CONTROL_ALLOW_ORIGIN中设置的域下的cookie
     */
    public String allowedOrigin;

    /**
     * 允许传进来的消息头，如果有自定义的Token之类的请求头，也需要添加进来,不然后台获取不到值
     * Origin,X-Requested-With,Content-Type,Accept,Cache-Control
     */
    public String allowedHeader;

    /**
     * GET,PUT,POST,DELETE,OPTIONS,HEAD
     */
    public String allowedMethod;

    /**
     * 允许暴露给浏览器的消息头，自定义消息相应头需要在此处指明
     * Origin,X-Requested-With,Content-Type,Accept,Cache-Control
     */
    public List<String> exposedHeaders;

    /**
     * 该字段可选，用来指定本次预检请求的有效期，单位为秒
     */
    public Long maxAge;

}
