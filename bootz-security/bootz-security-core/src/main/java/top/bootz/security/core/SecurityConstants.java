/**
 * 
 */
package top.bootz.security.core;

/**
 * 安全认证相关的常量
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月1日 下午5:53:04
 */
public final class SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    public static final String DEFAULT_VERIFICATION_CODE_URL_PREFIX = "/verification";

    /**
     * 当请求需要身份认证时，默认跳转的url
     * 
     * @see CoreSecurityController
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    public static final String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认的手机验证码登录请求处理url
     */
    public static final String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认的OPENID登录请求处理url
     */
    public static final String DEFAULT_SIGN_IN_PROCESSING_URL_OPENID = "/authentication/openid";

    /**
     * 默认注册页面
     */
    public static final String DEFAULT_SIGNUP_PAGE_URL = "/pages/default-sign-up.html";

    /**
     * 默认登录页面
     */
    public static final String DEFAULT_SIGNIN_PAGE_URL = "/pages/default-sign-in.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * openid参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";

    /**
     * providerId参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";

    /**
     * session失效默认的跳转地址
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/default-session-invalid.html";
    
    /**
     * 获取第三方用户信息的url
     */
    public static final String DEFAULT_SOCIAL_USER_INFO_URL = "/user/social/me";

    private SecurityConstants() {
    }

}
