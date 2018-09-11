package top.bootz.core.base.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月11日 下午11:04:31
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleTextData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String data;

    public SimpleTextData(String data) {
        this.data = data;
    }

}
