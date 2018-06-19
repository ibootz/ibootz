package top.bootz.user.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.attribute.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 用户组(岗位，自定义用户组)
 * 
 * @author John 2018年6月10日 下午6:53:40 <br/>
 */

@Entity
@Table(name = "uc_user_group", indexes = { @Index(columnList = "name", name = "idx_uc_usergroup_name", unique = true),
		@Index(columnList = "parent_id", name = "idx_uc_usergroup_parentid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private Long parentId;

	private DisableTypeEnum disable;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(20) default '' comment '用户组名'")
	public String getName() {
		return name;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(256) default '' comment '用户组中文描述'")
	public String getDescription() {
		return description;
	}

	@Column(name = "parent_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '父用户组id'")
	public Long getParentId() {
		return parentId;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
