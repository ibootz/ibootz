package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 关联表：权限-功能操作
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_authority_operation", indexes = {
		@Index(columnList = "authority_id,operation_id", name = "idx_uc_mao_authid_operationid", unique = true),
		@Index(columnList = "operation_id", name = "idx_uc_mao_operationid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapAuthorityOperation extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long authorityId;

	private Long operationId;

	@Column(name = "authority_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-权限ID'")
	public Long getAuthorityId() {
		return authorityId;
	}

	@Column(name = "operation_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-功能操作ID'")
	public Long getOperationId() {
		return operationId;
	}

}
