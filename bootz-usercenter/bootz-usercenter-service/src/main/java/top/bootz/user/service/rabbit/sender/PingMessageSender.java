package top.bootz.user.service.rabbit.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import top.bootz.commons.snowflake.IdGenerator;
import top.bootz.user.service.mongo.RabbitMessageLogService;
import top.bootz.user.service.rabbit.BaseMessageSender;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.rabbit.sender
 * @Description : Rabbit消息发送中心
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:01
 */

@Component
@NoArgsConstructor
public class PingMessageSender extends BaseMessageSender {

    @Autowired
    public PingMessageSender(RabbitMessageLogService rabbitMessageLogService, IdGenerator idGenerator,
            RabbitTemplate rabbitTemplate) {
        super(rabbitMessageLogService, idGenerator, rabbitTemplate);
    }

}
