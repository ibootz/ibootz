package top.bootz.user.entity.mysql.role;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.CommonEntity;
import top.bootz.core.converter.attribute.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 角色表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_role")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends CommonEntity {

    private static final long serialVersionUID = 1L;

    private String code;

    private String displayName;

    private DisableTypeEnum disable;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(20) default '' comment '角色Code(例如：root, admin, manager, cs ...)'")
    public String getCode() {
        return code;
    }

    @Column(name = "display_name", nullable = false, columnDefinition = "varchar(20) default '' comment '角色中文描述名(超级管理员, 管理员, 产品经理, 客服等)'")
    public String getDisplayName() {
        return displayName;
    }

    @Convert(converter = DisableTypeAttributeConverter.class)
    @Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
    public DisableTypeEnum getDisable() {
        return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
    }

}
