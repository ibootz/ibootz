package top.bootz.core.base.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import top.bootz.core.base.entity.BaseEntity;

/**
 * @Project : ibootz
 * @Package : top.bootz.core.base.dto
 * @Description : Rabbitmq传递消息时的统一消息实体
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:04
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RabbitMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /* 消息唯一编号，向其他组件传递消息时一定要为该消息创建一个唯一标识，方便追踪 */
    @NotNull
    private Long id;

    /* 消息主体 */
    @NotNull
    private Object payload;

}
