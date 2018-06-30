package top.bootz.user.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.core.base.entity.BaseEntity;

import java.time.LocalDateTime;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.entity.mongo
 * @Description : Rabbitmq消息日志表, 将消息发送实体、接收实体、确认实体存入mongodb，待查
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:35
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndexes({@CompoundIndex(name = "idx_rml_exchange_routingkey", def = "{'exchange': 1, 'routingKey': 1}")})
public class RabbitMessageLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    /**
     * 发送转发器
     */
    @Indexed
    private String exchange;

    /**
     * 发送路由
     */
    @Indexed
    private String routingKey;

    /**
     * 发送的消息实体
     */
    @Indexed
    private BaseMessage message;

    /**
     * 发送阶段：是否成功发送到消息服务器
     */
    @Indexed
    private Boolean sent;

    /**
     * 发送成功的时间
     */
    @Indexed
    private LocalDateTime sentTime;

    /**
     * 确认递送：broker服务器是否给出确认成功的回调
     */
    @Indexed
    private Boolean confirmed;

    /**
     * 确认成功的时间
     */
    @Indexed
    private LocalDateTime confirmedTime;

    /**
     * 确认失败时，返回的失败消息
     */
    private String unconfirmedCause;

    /**
     * 退回消息：broker服务器没有找到相应的exchange，退回了消息
     */
    @Indexed
    private Boolean returned;

    /**
     * 消息回退的时间
     */
    @Indexed
    private LocalDateTime returnedTime;

    /**
     * return退回消息时，附带的原因说明
     */
    private String returnedCause;

    /**
     * 处理阶段：消息是否被成功接收并成功处理
     */
    @Indexed
    private Boolean received;

    /**
     * 成功接收到消息的时间
     */
    @Indexed
    private LocalDateTime receivedTime;


    /**
     * 触发此条rabbit消息的用户
     */
    @Indexed
    @CreatedBy
    private Long createtor;

    /**
     * 触发时间
     */
    @Indexed
    @CreatedDate
    private LocalDateTime createTime = LocalDateTime.now();

    public RabbitMessageLog(String exchange, String routingKey, BaseMessage message, boolean sent) {
        super();
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.message = message;
        this.sent = sent;
    }

}
