package top.bootz.core.supporter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.bootz.core.base.entity.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PagingSupport<T extends Serializable> extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private List<T> results = new ArrayList<>();

    private PagingInfo pagingInfo;

}
