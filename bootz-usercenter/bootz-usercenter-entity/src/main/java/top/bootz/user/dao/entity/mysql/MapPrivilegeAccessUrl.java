package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

@Entity
@Table(name = "uc_map_privilege_access_url")
public class MapPrivilegeAccessUrl extends BaseEntity {

	private static final long serialVersionUID = 7737715396514997118L;

	private String privilegeId;

	private String accessUrlId;

	public MapPrivilegeAccessUrl(String privilegeId, String accessUrlId) {
		super();
		this.privilegeId = privilegeId;
		this.accessUrlId = accessUrlId;
	}

	public MapPrivilegeAccessUrl() {
		super();
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "access_url_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-可访问url前缀ID'")
	public String getAccessUrlId() {
		return accessUrlId;
	}

	public void setAccessUrlId(String accessUrlId) {
		this.accessUrlId = accessUrlId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessUrlId == null) ? 0 : accessUrlId.hashCode());
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
		MapPrivilegeAccessUrl other = (MapPrivilegeAccessUrl) obj;
		if (accessUrlId == null) {
			if (other.accessUrlId != null)
				return false;
		} else if (!accessUrlId.equals(other.accessUrlId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}
	
}
