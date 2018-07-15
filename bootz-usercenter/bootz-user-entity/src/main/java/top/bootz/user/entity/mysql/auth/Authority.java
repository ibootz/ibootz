package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.user.commons.converter.OperationAttributeConverter;
import top.bootz.user.commons.converter.ResourceAttributeConverter;
import top.bootz.user.commons.dictionary.OperationEnum;
import top.bootz.user.commons.dictionary.ResourceEnum;

/**
 * 权限表（动作 + 资源）
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_authority", indexes = {
        @Index(columnList = "operation,resource_id", name = "idx_uc_auth_ope_resid", unique = true),
        @Index(columnList = "resource_id", name = "idx_uc_auth_resourceid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseMysqlEntity {

    private static final long serialVersionUID = 1L;

    /** 动作类型 */
    private OperationEnum operation;

    /** 资源类型 */
    private ResourceEnum resourceType;

    /** 资源ID（指向Element, File, Menu, Operation, Url） */
    private Long resourceId;

    /** 权限描述 */
    private String description;

    @Convert(converter = OperationAttributeConverter.class)
    @Column(name = "operation", nullable = false, columnDefinition = "tinyint(1) default 0 comment '动作类型(view, create, modify, upload, download等)'")
    public OperationEnum getOperation() {
        return operation;
    }

    @Convert(converter = ResourceAttributeConverter.class)
    @Column(name = "resource_type", nullable = false, columnDefinition = "tinyint(1) default 0 comment '权限类型(menu, element, file, url等)'")
    public ResourceEnum getResourceType() {
        return resourceType;
    }

    @Column(name = "resource_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '资源id'")
    public Long getResourceId() {
        return resourceId;
    }

    @Column(name = "description", nullable = false, columnDefinition = "varchar(255) default '' comment '权限描述'")
    public String getDescription() {
        return description;
    }

}
