package top.bootz.commons.constant;

public final class SecurityConstants {

	private SecurityConstants() {
	}

	/**
	 * CROS跨域请求
	 */
	public static final String VALUE_ACCESS_CONTROL_ALLOW_CREDENTIALS = "true";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control,X-Auth-Token,Source";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_METHODS = "GET,PUT,POST,DELETE,OPTIONS,HEAD";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_ORIGIN = ".orion.com"; // 允许跨域请求的域名

	public static final String VALUE_ACCESS_CONTROL_EXPOSE_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control";

	public static final String VALUE_ACCESS_CONTROL_MAX_AGE = String.valueOf(60 * 60 * 24); // 预请求有效期保留一天(单位秒)

	/**
	 * 认证授权使用的数据库schema
	 */
	public static final String AUTH_DB = "orion_auth";

	/**
	 * Spring Security
	 */
	public static final String HEADER_AUTH_TOKEN = "X-Auth-Token"; // header中保存的JWT的key

	public static final String HEADER_NEW_AUTH_TOKEN = "X-New-Auth-Token"; // reponse的header中保存自动续期之后新生成的Token头信息

	public static final String HEADER_SOURCE = "Source"; // 请求头标识客户端类型的代号(Desktop, Android, IOS, H5...)
	
	public static final long JWT_EXPIRATION = 12 * 60 * 60 * 1000L; // JWT过期时间：12小时(单位毫秒)

	public static final long LEFT_TIME_FOR_TOKEN_EXPIRATION = 30 * 60 * 1000L; // 距离过期还剩的时间(单位毫秒)，用来确定自动刷新token的时间点:30分钟

	public static final String PUBLIC_KEY_FILE_PATH = "keystore/publicKey.keystore";

	public static final String PRIVATE_KEY_FILE_PATH = "keystore/privateKey.keystore";

	public static final long JWT_REFRESH_END_TIME = 7 * 24 * 60 * 60 * 1000L; // 可以使用过期token换取新token的最长时间区间:7天(单位毫秒)

}
