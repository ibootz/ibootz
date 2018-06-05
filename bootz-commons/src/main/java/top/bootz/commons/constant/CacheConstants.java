package top.bootz.commons.constant;

/**
 * <p/>
 * 定义系统中使用的CACHE的常量
 * <p/>
 */
public abstract class CacheConstants {

    /**
     * client Details Cache, key is clientId
     */
    public static final String CLIENT_DETAILS_CACHE = "clientDetailsCache";

    /**
     * access Token Cache, key is token
     */
    public static final String ACCESS_TOKEN_CACHE = "accessTokenCache";

    /**
     * refresh Token Cache, key is token
     */
    public static final String REFRESH_TOKEN_CACHE = "refreshTokenCache";

    /**
     * authorization Code Cache, key is code
     */
    public static final String AUTHORIZATION_CODE_CACHE = "authorizationCodeCache";

    /**
     * user Cache, key is username
     */
    public static final String USER_CACHE = "userCache";


    private CacheConstants() {
    }

}
