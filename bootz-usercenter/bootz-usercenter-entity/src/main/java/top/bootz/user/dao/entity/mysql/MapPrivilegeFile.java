package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;
/**
 * 权限文件映射表
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_privilege_file")
public class MapPrivilegeFile extends BaseEntity {

	private static final long serialVersionUID = 2456486329644182616L;
	
	private String privilegeId;
	
	private String fileId;

	public MapPrivilegeFile(String privilegeId, String fileId) {
		super();
		this.privilegeId = privilegeId;
		this.fileId = fileId;
	}

	public MapPrivilegeFile() {
		super();
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "file_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-文件ID'")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
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
		MapPrivilegeFile other = (MapPrivilegeFile) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}

}
