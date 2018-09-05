package top.bootz.user.entity.mysql.user;

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
 * 关联表：用户-角色
 * 
 * @author John <br/>
 * @time 2018年6月17日 下午2:04:39
 */
@Entity
@Table(name = "uc_map_user_role", indexes = {
        @Index(columnList = "user_id,role_id", name = "idx_uc_mur_userid_roleid", unique = true),
        @Index(columnList = "role_id", name = "idx_uc_mur_roleid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "userId", "roleId" }, callSuper = false)
public class MapUserRole extends CommonEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '用户ID'")
    public Long getUserId() {
        return userId;
    }

    @Column(name = "role_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '角色ID'")
    public Long getRoleId() {
        return roleId;
    }

}
