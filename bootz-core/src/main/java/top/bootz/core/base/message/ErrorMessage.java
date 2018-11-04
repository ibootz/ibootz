package top.bootz.core.base.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

/**
 * @author John
 * @time 2018年6月17日 下午11:46:10
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 标准http状态码 */
    private int httpStatus;
    
    /** 应用内部自定义错误代码 */
    private String code;

    /** 错误信息 */
    private String message;

    /** 附加更详细说明 */
    private String moreInfo;

    /** 堆栈信息 */
    @JsonIgnore
    private Throwable throwable;

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
