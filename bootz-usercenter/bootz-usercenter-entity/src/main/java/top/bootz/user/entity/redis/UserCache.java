package top.bootz.user.entity.redis;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;
import top.bootz.commons.helper.BeanHelper;
import top.bootz.core.dictionary.DisableTypeEnum;
import top.bootz.core.dictionary.GenderEnum;
import top.bootz.core.dictionary.LockStatusEnum;
import top.bootz.user.entity.mysql.auth.Authority;
import top.bootz.user.entity.mysql.user.User;

/**
 * 
 * @author John
 * @time 2018年6月17日 上午10:49:25
 */
@Setter
@Getter
@RedisHash(timeToLive = 86400) // 缓存一天
public class UserCache implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	/** 登录用户名 */
	@Indexed
	private String username;

	/** 加密之后的密码(加密算法：BCrypt) */
	private String password;

	/** 员工姓名 */
	@Indexed
	private String realName;

	/** 性别 */
	private GenderEnum gender;

	/** 电话 */
	@Indexed
	private String mobile;

	/** 身份证 */
	private String idCard;

	/** 邮箱 */
	@Indexed
	private String email;

	/** 启用/禁用 */
	private DisableTypeEnum disable;

	/** 锁定时间 */
	private LocalDateTime disableTime;

	/** 是否锁定 */
	private LockStatusEnum locked;

	/** 上次锁定时间 */
	private LocalDateTime lastLockTime;

	/** 上次登录时间 */
	private LocalDateTime lastLoginTime;

	/** 上次重置密码的时间 */
	private LocalDateTime lastPasswordResetTime;

	/** 用户拥有的权限分类合集(key为AuthorityEnum中的desc字段，代表相应类型的权限) */
	@Indexed
	private Set<Authority> authorities = Collections.synchronizedSet(new HashSet<>());

	public UserCache(User user, Set<Authority> authorities) {
		BeanHelper.copyProperties(user, this);
		this.authorities = authorities;
	}

}
