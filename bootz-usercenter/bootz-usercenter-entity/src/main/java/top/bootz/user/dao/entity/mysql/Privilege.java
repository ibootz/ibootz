package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;
import top.bootz.user.commons.dictionary.PrivilegeEnum;

/**
 * 权限表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_privilege")
public class Privilege extends BaseEntity {

	private static final long serialVersionUID = 5240662074365174587L;

	private PrivilegeEnum type; // 权限类型

	private String description; // 权限描述

	@Column(name = "type", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '权限类型(menu, element, file, operation等)'")
	public PrivilegeEnum getType() {
		return type;
	}

	public void setType(PrivilegeEnum type) {
		this.type = type;
	}

	@Column(name = "description", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '权限描述'")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
