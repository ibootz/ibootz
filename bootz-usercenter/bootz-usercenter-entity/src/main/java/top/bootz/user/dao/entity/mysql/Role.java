package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * 角色表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = -6640269699963057898L;

	private String name;

	private String displayName;

	private Boolean enable;

	@Column(name = "enable", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "name", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '角色名(root, admin, pm, cs ...)'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "display_name", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '角色中文描述名(超级管理员, 管理员, 产品经理, 客服等)'")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
