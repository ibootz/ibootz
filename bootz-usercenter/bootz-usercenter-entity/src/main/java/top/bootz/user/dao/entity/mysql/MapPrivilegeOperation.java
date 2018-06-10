package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * 权限功能操作映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_privilege_menu")
public class MapPrivilegeOperation extends BaseEntity {

	private static final long serialVersionUID = -1046830927806702814L;

	private String privilegeId;

	private String operationId;

	public MapPrivilegeOperation(String privilegeId, String operationId) {
		super();
		this.privilegeId = privilegeId;
		this.operationId = operationId;
	}

	public MapPrivilegeOperation() {
		super();
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "operation_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-功能操作ID'")
	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operationId == null) ? 0 : operationId.hashCode());
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
		MapPrivilegeOperation other = (MapPrivilegeOperation) obj;
		if (operationId == null) {
			if (other.operationId != null)
				return false;
		} else if (!operationId.equals(other.operationId))
			return false;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		return true;
	}

}
