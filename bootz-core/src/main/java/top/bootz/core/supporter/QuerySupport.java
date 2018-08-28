package top.bootz.core.supporter;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySupport<T extends Serializable> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private T query;

    private PagingInfo paging;

}
