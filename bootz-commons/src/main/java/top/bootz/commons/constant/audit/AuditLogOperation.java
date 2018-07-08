package top.bootz.commons.constant.audit;

/**
 * 审计日志记录中使用的操作日志类型
 * 
 * @author John
 *
 */
public enum AuditLogOperation {
    ADD_ONE("addOne"), ADD_MANY("addMany"), DELETE_ONE("deleteOne"), DELETE_MANY("deleteMany"), MODIFY_ONE(
            "modifyOne"), MODIFY_MANY("modifyMany"), FIND_ONE("findOne"), FIND_MANY("findMany"), SEARCH("search");

    private String desc;

    private AuditLogOperation(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

}
