package top.bootz.commons.helper;

/**
 * @Project : ibootz
 * @Package : top.bootz.commons.helper
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-29 下午9:01
 */
public class BooleanHelper {

    private BooleanHelper() {
    }

    /**
     * 将Boolean包装类转换为boolean基本类型, 杜绝空指针风险
     *
     * @param bool
     * @return
     */
    public static boolean toSafeBoolean(Boolean bool) {
        return bool == null ? false : bool;
    }

}
