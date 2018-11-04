package top.bootz.security.core.properties.session;

import lombok.Data;
import top.bootz.security.core.SecurityConstants;

@Data
public class SessionProperties {

    public static final int TWO_WEEKS_S = 1209600;

    /** 系统管理员可以通过修改该值来使所有用户的记住我token失效 */
    public static final String DEFAULT_REMEMBER_ME_KEY = "default_remember_me_key";

    /** 注册页面路径 */
    private String signUpUrl = SecurityConstants.DEFAULT_SIGNUP_PAGE_URL;

    /** 登录页面路径，如果使用我们安全组件的应用没有配置，则使用此处配置的默认值 */
    private String signInUrl = SecurityConstants.DEFAULT_SIGNIN_PAGE_URL;

    /** 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。 */
    private String signOutUrl;

    /** 登录认证成功或者失败之后，是采用跳转的方式还是采用返回json格式响应的方式来应对，如果应用未配置，默认返回json响应 */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /** 记住我功能的过期时间 */
    private int rememberMeSeconds = TWO_WEEKS_S;

    /** 记住我功能的KEY */
    private String rememberMeKey = DEFAULT_REMEMBER_ME_KEY;

    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    /** 同一个用户在系统中的最大session数，默认1 */
    private int maximumSessions = 1;

    /** 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉 */
    private boolean maxSessionsPreventsLogin;

}
