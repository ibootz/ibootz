package top.bootz.user.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import top.bootz.core.base.entity.BaseEntity;

import java.time.LocalDateTime;


/**
 * @Project : ibootz
 * @Package : top.bootz.user.entity.mongo
 * @Description : Rabbitmq发送消息日志表
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:35
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RabbitSendMessageLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    /* 发送渠道 */
    @Indexed
    private String channel;

    /* 发送转发器 */
    @Indexed
    private String exchange;

    /* 发送路由 */
    @Indexed
    private String router;

    /* 发送目的地消息队列 */
    @Indexed
    private String queue;

    /* 消息主体 */
    private Object payload;

    @Indexed
    @CreatedDate
    private LocalDateTime createTime;

}
