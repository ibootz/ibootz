package top.bootz.core.base.view;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月11日 下午11:05:55
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleListData<T extends Serializable> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private List<T> datas = Collections.emptyList();

    public SimpleListData(List<T> datas) {
        this.datas = datas;
    }

}
