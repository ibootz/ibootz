package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * 权限菜单映射表
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_privilege_element")
public class MapPrivilegeElement extends BaseEntity {

	private static final long serialVersionUID = -7208177982189783654L;

	private String privilegeId;

	private String elementId;

	public MapPrivilegeElement(String privilegeId, String elementId) {
		super();
		this.privilegeId = privilegeId;
		this.elementId = elementId;
	}

	public MapPrivilegeElement() {
		super();
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "element_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-页面元素ID'")
	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((privilegeId == null) ? 0 : privilegeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapPrivilegeElement other = (MapPrivilegeElement) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}

}
