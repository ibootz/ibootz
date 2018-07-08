package top.bootz.user.entity.mysql.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 关联表：用户组-角色
 * 
 * @author John
 * @time 2018年6月17日 下午12:07:08
 */

@Entity
@Table(name = "uc_map_user_group_role", indexes = {
        @Index(columnList = "usergroup_id,role_id", name = "idx_uc_mugr_usergroupid_roleid", unique = true),
        @Index(columnList = "role_id", name = "idx_uc_mugr_roleid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "userGroupId", "roleId" }, callSuper = false)
public class MapUserGroupRole extends BaseMysqlEntity {

    private static final long serialVersionUID = 1L;

    private Long userGroupId;

    private Long roleId;

    @Column(name = "usergroup_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户组ID'")
    public Long getUserGroupId() {
        return userGroupId;
    }

    @Column(name = "role_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '角色ID'")
    public Long getRoleId() {
        return roleId;
    }

}
