package top.bootz.core.base.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import top.bootz.core.base.entity.BaseEntity;

import java.time.LocalDateTime;

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
public class RabbitMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 消息编号，rabbitmq服务器生成的消息唯一标识
     */
    private Long id;

    /**
     * 消息来自哪个微应用
     */
    @Indexed
    private String from;

    /**
     * 消息去往哪个微应用
     */
    @Indexed
    private String[] to;

    /**
     * 触发此条rabbit消息的用户
     */
    @Indexed
    private Long createtor;

    /**
     * 触发时间
     */
    @Indexed
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime = LocalDateTime.now();

    public RabbitMessage(String from, String[] to, Long createtor, LocalDateTime createTime) {
        super();
        this.from = from;
        this.to = to;
        this.createtor = createtor;
        this.createTime = createTime;
    }

}
