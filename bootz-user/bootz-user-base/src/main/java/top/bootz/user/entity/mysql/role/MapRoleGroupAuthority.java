package top.bootz.user.entity.mysql.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.CommonEntity;

/**
 * 关联表：角色组-权限
 * 
 * @author John
 * @time 2018年6月17日 下午12:07:08
 */

@Entity
@Table(name = "uc_map_rolegroup_authority", indexes = {
        @Index(columnList = "group_id,auth_id", name = "idx_uc_mrga_groupid_authid", unique = true),
        @Index(columnList = "auth_id", name = "idx_uc_mrga_authid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "groupId", "authId" }, callSuper = false)
public class MapRoleGroupAuthority extends CommonEntity {

    private static final long serialVersionUID = 1L;

    private Long groupId;

    private Long authId;

    @Column(name = "group_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '角色组ID'")
    public Long getGroupId() {
        return groupId;
    }

    @Column(name = "auth_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '权限ID'")
    public Long getAuthId() {
        return authId;
    }

}
