package top.bootz.user.dao.entity.mysql;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import top.bootz.commons.helper.ArrayHelper;
import top.bootz.core.base.entity.BaseEntity;
import top.bootz.user.commons.converter.GenderAttributeConverter;
import top.bootz.user.commons.dictionary.GenderEnum;

/**
 * 用户表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = -7906916919904469152L;

	private String username; // 登录用户名

	private String password; // 加密之后的密码(加密算法：BCrypt)

	private String realName; // 员工姓名

	private GenderEnum gender; // 性别

	private String roles; // role角色字符串拼接，冗余字段

	private String mobile;

	private String idCard;

	private String email;

	private Boolean enable;

	private Boolean locked;

	private LocalDateTime lastLoginTime;

	private LocalDateTime lastPasswordResetTime;

	@Column(name = "username", nullable = false, length = 255, unique = true, columnDefinition = "varchar(255) default '' comment '用户名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "enable", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public Boolean getEnable() {
		if (enable == null) {
			enable = true;
		}
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "mobile", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '电话'")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "idcard", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '身份证号码'")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "last_login_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后登录时间'")
	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Convert(converter = GenderAttributeConverter.class)
	@Column(name = "gender", nullable = false, length = 10, columnDefinition = "varchar(10) default '' comment '性别'")
	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	@Column(name = "realname", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '真实姓名'")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "email", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '邮箱'")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "last_password_reset_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后一次密码重置的时间'")
	public LocalDateTime getLastPasswordResetTime() {
		return lastPasswordResetTime;
	}

	public void setLastPasswordResetTime(LocalDateTime lastPasswordResetTime) {
		this.lastPasswordResetTime = lastPasswordResetTime;
	}

	@Column(name = "locked", nullable = false, columnDefinition = "bit(1) default 0 comment '是否被锁定'")
	public Boolean getLocked() {
		if (locked == null) {
			locked = false;
		}
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@Column(name = "roles", nullable = false, length = 512, columnDefinition = "varchar(512) default '' comment '角色字符串的拼接(root,admin,pm,cs)，冗余字段'")
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		List<String> roleList = new ArrayList<>();
		if (StringUtils.isNotBlank(roles)) {
			String[] roleArr = ArrayHelper.trimElems(roles.split(","));
			if (ArrayUtils.isNotEmpty(roleArr)) {
				roleList = Arrays.asList(roleArr);
			}
		}
		return roleList;
	}

}
