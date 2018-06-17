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
 * 关联表：权限-页面元素
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_authority_element", indexes = {
		@Index(columnList = "authority_id,element_id", name = "idx_uc_mae_authid_elementid", unique = true),
		@Index(columnList = "element_id", name = "idx_uc_mae_elementid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapAuthorityElement extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long authorityId;

	private Long elementId;

	@Column(name = "authority_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-权限ID'")
	public Long getAuthorityId() {
		return authorityId;
	}

	@Column(name = "element_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-页面元素ID'")
	public Long getElementId() {
		return elementId;
	}

}
