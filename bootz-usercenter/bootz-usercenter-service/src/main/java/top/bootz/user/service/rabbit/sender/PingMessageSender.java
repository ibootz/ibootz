package top.bootz.user.service.rabbit.sender;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import top.bootz.commons.snowflake.IdGenerator;
import top.bootz.core.base.dto.BaseMessage;
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
public class PingMessageSender extends BaseMessageSender {

	@Autowired
	public PingMessageSender(RabbitMessageLogService rabbitMessageLogService, IdGenerator idGenerator,
			RabbitTemplate rabbitTemplate) {
		super(rabbitMessageLogService, idGenerator, rabbitTemplate);
		rabbitTemplate.setConfirmCallback(this);
	}

	/** 这里覆盖包装一下父类的方法，是为了用上异步注解 */
	@Async
	@Override
	public void send(String exchange, String routingKey, BaseMessage message, Map<String, Object> messageHeaders) {
		super.send(exchange, routingKey, message, messageHeaders);
	}

}
