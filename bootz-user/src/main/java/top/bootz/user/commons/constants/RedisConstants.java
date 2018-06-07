package top.bootz.user.commons.constants;

/**
 * Redis相关常量
 * <p>
 * 命名规范，字段名大写，单词之间采用下划线分割; 字段值全部小写；
 * 必须写明每个常量的业务意义。
 * </p>
 * 
 * @author John
 *
 */
public final class RedisConstants {

	private RedisConstants() {

	}

	/**
	 * 测试Redis服务器连接情况时使用的key
	 */
	public static final String PING = "ping";

	/**
	 * 用户登录后，保存其相关token和身份认证信息的key
	 */
	public static final String KEY_TOKEN_USERINFO = "source||token:";

	/**
	 * 保存新的useid:token键值对，同时根据旧token清理token:useinfo表中的旧数据
	 */
	public static final String KEY_USERID_TOKEN = "source||userid:";

	public static final String KEY_TO_BE_EXPIRED_TOKEN = "expired_token:";

}
