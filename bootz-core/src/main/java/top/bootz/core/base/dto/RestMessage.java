package top.bootz.core.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;
import top.bootz.core.dictionary.MessageStatusEnum;

/**
 * 用户API层面错误消息的传递实体
 * 
 * @author John
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息类型： <br>
	 * Success - 成功 <br>
	 * Error - 失败 <br>
	 * Warnning - 警告，标示虽然此次请求成功，但是过程中有一些非阻塞的问题存在 <br>
	 */
	private MessageStatusEnum status;

	/** 业务数据 */
	@JsonInclude(value = Include.NON_EMPTY)
	private Object data;

	/**
	 * 如果status字段不是Success，该属性将会填充详细说明信息<br>
	 * 如果是null，那么不参与格式化
	 */
	@JsonInclude(value = Include.NON_EMPTY)
	private ErrorMessage error;

}
