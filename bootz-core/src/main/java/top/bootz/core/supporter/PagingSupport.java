package top.bootz.core.supporter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class PagingSupport<T extends Serializable> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private List<T> results = new ArrayList<>();

    private PagingInfo pagingInfo;

}
