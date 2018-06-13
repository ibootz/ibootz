package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 菜单表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_menu")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private String code;

	private String displayName;

	private Long parentId;

	private Integer level;

	private String path;

	private String accessUrl;

	private Integer orderIndex;

	private String description;

	@Column(name = "code", nullable = false, columnDefinition = "varchar(32) default '' comment '菜单编号'")
	public String getCode() {
		return code;
	}

	@Column(name = "display_name", nullable = false, columnDefinition = "varchar(32) default '' comment '菜单显示名'")
	public String getDisplayName() {
		return displayName;
	}

	@Column(name = "parent_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '父菜单ID'")
	public Long getParentId() {
		return parentId;
	}

	@Column(name = "level", nullable = false, columnDefinition = "tinyint(2) default 0 comment '菜单级数'")
	public Integer getLevel() {
		return level;
	}

	@Column(name = "path", nullable = false, columnDefinition = "varchar(512) default '' comment '父子菜单关系路径'")
	public String getPath() {
		return path;
	}

	@Column(name = "access_url", nullable = false, columnDefinition = "varchar(255) default '' comment '访问路径'")
	public String getAccessUrl() {
		return accessUrl;
	}

	@Column(name = "order_index", nullable = false, columnDefinition = "smallint(8) default 0 comment '菜单排序索引'")
	public Integer getOrderIndex() {
		return orderIndex;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(512) default '' comment '描述'")
	public String getDescription() {
		return description;
	}

}
