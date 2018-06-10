package top.bootz.user.dao.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * Desc: 角色组
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午6:53:40 <br/>
 */

@Entity
@Table(name = "uc_role_group")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleGroup extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private String groupName;

	private String desc;

	@Column(name = "group_name", nullable = false, columnDefinition = "varchar(20) default '' comment '角色组中文名'")
	public String getGroupName() {
		return groupName;
	}

	@Column(name = "desc", nullable = false, columnDefinition = "varchar(256) default '' comment '角色中文描述'")
	public String getDesc() {
		return desc;
	}

}
