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
 * 关联表：权限-菜单
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_authority_menu", indexes = {
		@Index(columnList = "authority_id,menu_id", name = "idx_uc_mam_authid_menuid", unique = true),
		@Index(columnList = "menu_id", name = "idx_uc_mam_menuid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapAuthorityMenu extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long authorityId;

	private Long menuId;

	@Column(name = "authority_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-权限ID'")
	public Long getAuthorityId() {
		return authorityId;
	}

	@Column(name = "menu_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-菜单ID'")
	public Long getMenuId() {
		return menuId;
	}

}
