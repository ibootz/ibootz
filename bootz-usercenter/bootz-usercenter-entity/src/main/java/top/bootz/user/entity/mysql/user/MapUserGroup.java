package top.bootz.user.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 关联表：用户-用户组
 * 
 * @author John
 * @time 2018年6月17日 下午12:07:08
 */

@Entity
@Table(name = "uc_map_user_group", indexes = {
		@Index(columnList = "user_id,group_id", name = "idx_uc_mug_userid_groupid", unique = true),
		@Index(columnList = "group_id", name = "idx_uc_mug_groupid") })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "userId", "groupId" }, callSuper = false)
public class MapUserGroup extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private Long groupId;

	@Column(name = "user_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户ID'")
	public Long getUserId() {
		return userId;
	}

	@Column(name = "group_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户组ID'")
	public Long getGroupId() {
		return groupId;
	}

}
