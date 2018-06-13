package top.bootz.user.entity.mysql.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 部门表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_department")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 部门名 */
	private String name;

	/** 部门路径 */
	private String path;

	/** 父部门id */
	private Long parentId;

	/** 部门职能描述 */
	private String description;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' comment '部门名'")
	public String getName() {
		return name;
	}

	@Column(name = "path", nullable = false, columnDefinition = "varchar(512) default '' comment '部门路径'")
	public String getPath() {
		return path;
	}

	@Column(name = "parentId", nullable = false, columnDefinition = "bigint(64) default 0 comment '父部门id'")
	public Long getParentId() {
		return parentId;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(255) default '' comment '部门职能描述'")
	public String getDescription() {
		return description;
	}

}
