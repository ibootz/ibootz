package top.bootz.commons.constant.audit;

/**
 * 审计日志中操作的类型信息
 * 
 * @author John
 *
 */
public enum AuditLogObject {

    PMS_ORDER("order");

    private String desc;

    private AuditLogObject(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}
