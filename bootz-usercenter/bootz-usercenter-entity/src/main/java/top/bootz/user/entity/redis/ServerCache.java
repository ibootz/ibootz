package top.bootz.user.entity.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 储存当前所在服务器相关信息和应用本身的信息
 * 
 * @author John
 * @time 2018年6月24日 下午6:04:00
 */

@Setter
@Getter
@NoArgsConstructor
@RedisHash(timeToLive = 2592000) // 缓存一个月
public class ServerCache extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	/** 本机host */
	@Indexed
	private String host;

	/** 应用名 */
	@Indexed
	private String appName;

}
