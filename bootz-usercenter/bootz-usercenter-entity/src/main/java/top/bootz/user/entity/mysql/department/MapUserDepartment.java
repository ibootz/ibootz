package top.bootz.user.entity.mysql.department;

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
 * 关联表：用户-部门
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_user_department", indexes = {
		@Index(columnList = "user_id,department_id", name = "idx_uc_mud_userid_departmentid", unique = true),
		@Index(columnList = "department_id", name = "idx_uc_mud_departmentid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "userId", "departmentId" }, callSuper = false)
public class MapUserDepartment extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 用户id */
	private Long userId;

	/** 部门id */
	private Long departmentId;

	@Column(name = "user_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户id'")
	public Long getUserId() {
		return userId;
	}

	@Column(name = "department_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '部门id'")
	public Long getDepartmentId() {
		return departmentId;
	}

}
