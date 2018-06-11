package top.bootz.user.entity.mysql.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 用户部门映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_user_department")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapUserDepartment extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 用户id */
	private Long userId;

	/** 部门id */
	private Long departmentId;

	@Column(name = "userId", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户id'")
	public Long getUserId() {
		return userId;
	}

	@Column(name = "departmentId", nullable = false, columnDefinition = "bigint(64) default 0 comment '部门id'")
	public Long getDepartmentId() {
		return departmentId;
	}

}
