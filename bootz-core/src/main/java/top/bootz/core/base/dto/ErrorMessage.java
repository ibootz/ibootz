package top.bootz.core.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 
 * @author John
 * @time 2018年6月17日 下午11:46:10
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 应用内部自定义错误代码 */
    private String code;

    /** 错误信息 */
    private String message;

    /** 附加更详细说明 */
    private String moreInfo;

    /** 堆栈信息 */
    @JsonIgnore
    private Throwable throwable;

}
