package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * 权限菜单映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_privilege_menu")
public class MapPrivilegeMenu extends BaseEntity {

	private static final long serialVersionUID = -5373816674121902181L;

	private String menuId;

	private String privilegeId;

	public MapPrivilegeMenu(String menuId, String privilegeId) {
		super();
		this.menuId = menuId;
		this.privilegeId = privilegeId;
	}

	public MapPrivilegeMenu() {
		super();
	}

	@Column(name = "menu_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-菜单ID'")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
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
		MapPrivilegeMenu other = (MapPrivilegeMenu) obj;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}

}
