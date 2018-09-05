package top.bootz.user.entity.mysql.org;

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
 * 公司表
 * 
 * @author John
 * @time 2018年6月17日 下午4:36:43
 */

@Entity
@Table(name = "uc_org", indexes = { @Index(columnList = "name", name = "idx_uc_org_name", unique = true),
        @Index(columnList = "code", name = "idx_uc_org_code"),
        @Index(columnList = "parent_id", name = "idx_uc_org_parentid"),
        @Index(columnList = "full_name", name = "idx_uc_org_fullname"),
        @Index(columnList = "contact_name", name = "idx_uc_org_contactname"),
        @Index(columnList = "phone", name = "idx_uc_org_phone") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "code" }, callSuper = false)
public class Org extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /** 公司简称 */
    private String name;

    /** 公司唯一编号 */
    private String code;

    /** 父公司Id */
    private Long parentId;

    /** 公司全称 */
    private String fullName;

    /** 公司所在地 */
    private String address;

    /** 公司联系人 */
    private String contactName;

    /** 联系人电话 */
    private String phone;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' comment '公司简称'")
    public String getName() {
        return name;
    }

    @Column(name = "code", nullable = false, columnDefinition = "varchar(32) default '' comment '公司唯一编号'")
    public String getCode() {
        return code;
    }

    @Column(name = "parent_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '父公司ID'")
    public Long getParentId() {
        return parentId;
    }

    @Column(name = "full_name", nullable = false, columnDefinition = "varchar(64) default '' comment '公司全称'")
    public String getFullName() {
        return fullName;
    }

    @Column(name = "address", nullable = false, columnDefinition = "varchar(255) default '' comment '公司所在地址'")
    public String getAddress() {
        return address;
    }

    @Column(name = "contact_name", nullable = false, columnDefinition = "varchar(64) default '' comment '公司联系人姓名'")
    public String getContactName() {
        return contactName;
    }

    @Column(name = "phone", nullable = false, columnDefinition = "varchar(32) default '' comment '公司联系人电话'")
    public String getPhone() {
        return phone;
    }

}
