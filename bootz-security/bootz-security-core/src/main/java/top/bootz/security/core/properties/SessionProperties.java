package top.bootz.security.core.properties;

import lombok.Data;
import top.bootz.security.core.SecurityConstants;

@Data
public class SessionProperties {

    public static final int TWO_WEEKS_S = 1209600;

    /** 系统管理员可以通过修改该值来使所有用户的记住我token失效 */
    public static final String DEFAULT_REMEMBER_ME_KEY = "default_remember_me_key";

    /** 登录页面路径，如果使用我们安全组件的应用没有配置，则使用此处配置的默认值 */
    private String loginPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    /** 登录认证成功或者失败之后，是采用跳转的方式还是采用返回json格式响应的方式来应对，如果应用未配置，默认返回json响应 */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /** 记住我功能的过期时间 */
    private int rememberMeSeconds = TWO_WEEKS_S;

    /** 记住我功能的KEY */
    private String rememberMeKey = DEFAULT_REMEMBER_ME_KEY;

}
