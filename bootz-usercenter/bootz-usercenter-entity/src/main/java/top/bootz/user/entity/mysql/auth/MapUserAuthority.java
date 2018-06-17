package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 关联表：角色-权限
 * 
 * @author John
 * @time 2018年6月17日 下午12:07:08
 */

@Entity
@Table(name = "uc_map_user_authority", indexes = {
		@Index(columnList = "user_id,auth_id", name = "idx_uc_mua_userid_authid", unique = true),
		@Index(columnList = "auth_id", name = "idx_uc_mua_authid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "userId", "authId" }, callSuper = false)
public class MapUserAuthority extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private Long authId;

	@Column(name = "user_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户ID'")
	public Long getUserId() {
		return userId;
	}

	@Column(name = "auth_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '权限ID'")
	public Long getAuthId() {
		return authId;
	}

}
