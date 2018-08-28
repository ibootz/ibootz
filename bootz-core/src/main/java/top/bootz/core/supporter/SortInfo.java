package top.bootz.core.supporter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.bootz.core.base.entity.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SortInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String sortFiled;

    private String direction;

    private Integer priority; // 排序优先级

}
