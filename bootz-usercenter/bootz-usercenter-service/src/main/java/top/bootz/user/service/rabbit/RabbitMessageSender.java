package top.bootz.user.service.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.bootz.core.base.dto.RabbitMessage;
import top.bootz.user.entity.mongo.RabbitSendMessageLog;
import top.bootz.user.service.mongo.RabbitSendMessageLogService;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.rabbit.sender
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:01
 */


@Component
public class RabbitMessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RabbitSendMessageLogService rabbitSendMessageLogService;

    public void send(RabbitMessage message) {
        RabbitSendMessageLog sendMessageLog = new RabbitSendMessageLog();
        rabbitSendMessageLogService.asyncSave(sendMessageLog);
        rabbitTemplate.convertSendAndReceive(message);
    }
}
