package top.bootz.commons.helper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月23日 上午9:54:33
 */
public class CollectionHelper {

    private CollectionHelper() {
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... t) {
        Set<T> set = new HashSet<>();
        if (t == null) {
            return set;
        }

        for (T tmp : t) {
            set.add(tmp);
        }
        return set;
    }

}
