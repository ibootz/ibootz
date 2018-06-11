package top.bootz.user.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

@Entity
@Table(name = "uc_map_user_role")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapUserRole extends BaseMysqlEntity {

	private static final long serialVersionUID = 7623237985920744323L;

	private Long userId;

	private Long roleId;

	@Column(name = "user_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户ID'")
	public Long getUserId() {
		return userId;
	}

	@Column(name = "role_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '角色ID'")
	public Long getRoleId() {
		return roleId;
	}

}
