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
 * 关联表：角色-权限
 * 
 * @author John
 * @time 2018年6月17日 下午12:07:08
 */

@Entity
@Table(name = "uc_map_role_authority", indexes = {
        @Index(columnList = "role_id,auth_id", name = "idx_uc_mra_roleid_authid", unique = true),
        @Index(columnList = "auth_id", name = "idx_uc_mra_authid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "roleId", "authId" }, callSuper = false)
public class MapRoleAuthority extends CommonEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long authId;

    @Column(name = "role_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '角色ID'")
    public Long getRoleId() {
        return roleId;
    }

    @Column(name = "auth_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '权限ID'")
    public Long getAuthId() {
        return authId;
    }

}
