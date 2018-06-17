package top.bootz.user.entity.mysql.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.converter.GenderAttributeConverter;
import top.bootz.core.converter.LockStatusAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;
import top.bootz.core.dictionary.GenderEnum;
import top.bootz.core.dictionary.LockStatusEnum;

/**
 * 用户表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_user", indexes = { @Index(columnList = "username", name = "idx_uc_user_username", unique = true),
		@Index(columnList = "password", name = "idx_uc_user_password"),
		@Index(columnList = "realname", name = "idx_uc_user_realname"),
		@Index(columnList = "mobile", name = "idx_uc_user_mobile"),
		@Index(columnList = "email", name = "idx_uc_user_email") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "username" }, callSuper = false)
public class User extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 登录用户名 */
	private String username;

	/** 加密之后的密码(加密算法：BCrypt) */
	private String password;

	/** 员工姓名 */
	private String realName;

	/** 性别 */
	private GenderEnum gender;

	/** 电话 */
	private String mobile;

	/** 身份证 */
	private String idCard;

	/** 邮箱 */
	private String email;

	/** 是否禁用 */
	private DisableTypeEnum disable;

	/** 禁用时间 */
	private LocalDateTime disableTime;

	/** 是否锁定 */
	private LockStatusEnum locked;

	/** 上次锁定时间 */
	private LocalDateTime lastLockTime;

	/** 上次登录时间 */
	private LocalDateTime lastLoginTime;

	/** 上次重置密码的时间 */
	private LocalDateTime lastPasswordResetTime;

	@Column(name = "username", nullable = false, columnDefinition = "varchar(64) default '' comment '用户名'")
	public String getUsername() {
		return this.username;
	}

	@Column(name = "password", nullable = false, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return this.password;
	}

	@Column(name = "mobile", nullable = false, columnDefinition = "varchar(20) default '000-0000-0000' comment '电话'")
	public String getMobile() {
		return this.mobile;
	}

	@Column(name = "idcard", nullable = false, columnDefinition = "varchar(24) default '000000-00000000-0000' comment '身份证号码'")
	public String getIdCard() {
		return this.idCard;
	}

	@Column(name = "last_login_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后登录时间'")
	public LocalDateTime getLastLoginTime() {
		return this.lastLoginTime;
	}

	@Convert(converter = GenderAttributeConverter.class)
	@Column(name = "gender", nullable = false, columnDefinition = "char(1) default 'u' comment '性别'")
	public GenderEnum getGender() {
		return this.gender;
	}

	@Column(name = "realname", nullable = false, columnDefinition = "varchar(128) default '' comment '真实姓名'")
	public String getRealName() {
		return this.realName;
	}

	@Column(name = "email", nullable = false, columnDefinition = "varchar(128) default '' comment '邮箱'")
	public String getEmail() {
		return this.email;
	}

	@Column(name = "last_password_reset_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后一次密码重置的时间'")
	public LocalDateTime getLastPasswordResetTime() {
		return this.lastPasswordResetTime;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

	@Column(name = "disable_time", nullable = true, columnDefinition = "DATETIME default NULL comment '禁用时间'")
	public LocalDateTime getDisableTime() {
		return disableTime;
	}

	@Convert(converter = LockStatusAttributeConverter.class)
	@Column(name = "locked", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否锁定（0-未锁定，1-锁定）'")
	public LockStatusEnum getLocked() {
		return this.locked == null ? LockStatusEnum.NOT_LOCKED : this.locked;
	}

	@Column(name = "last_lock_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最近一次被锁定的时间'")
	public LocalDateTime getLastLockTime() {
		return lastLockTime;
	}

}
