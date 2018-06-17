package top.bootz.user.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 角色组
 * 
 * @author John 2018年6月10日 下午6:53:40 <br/>
 */

@Entity
@Table(name = "uc_role_group", indexes = { @Index(columnList = "name", name = "idx_uc_rolegroup_name", unique = true),
		@Index(columnList = "parent_id", name = "idx_uc_rolegroup_parentid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroup extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private Long parentId;

	private DisableTypeEnum disable;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(20) default '' comment '角色组名'")
	public String getName() {
		return name;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(256) default '' comment '角色组描述'")
	public String getDescription() {
		return description;
	}

	@Column(name = "parent_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '父角色组ID'")
	public Long getParentId() {
		return parentId;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
