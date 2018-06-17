package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 菜单表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_menu", indexes = { @Index(columnList = "code", name = "idx_uc_menu_code", unique = true),
		@Index(columnList = "parent_id", name = "idx_uc_menu_parentid"),
		@Index(columnList = "path", name = "idx_uc_menu_path") })
@Setter
@EqualsAndHashCode(of = { "code" }, callSuper = false)
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

	private DisableTypeEnum disable;

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

	@Column(name = "level", nullable = false, columnDefinition = "tinyint(2) default 0 comment '菜单级数，从1开始'")
	public Integer getLevel() {
		if ((this.level == null || this.level == 0) && StringUtils.isNotBlank(this.path)) {
			return this.path.split("-").length;
		}
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

	@Column(name = "order_index", nullable = false, columnDefinition = "smallint(8) default 0 comment '菜单序号'")
	public Integer getOrderIndex() {
		return orderIndex;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(512) default '' comment '描述'")
	public String getDescription() {
		return description;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
