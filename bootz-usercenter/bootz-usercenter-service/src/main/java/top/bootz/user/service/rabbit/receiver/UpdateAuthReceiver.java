package top.bootz.user.service.rabbit.receiver;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.user.commons.constants.RabbitConstants;
import top.bootz.user.service.mongo.RabbitMessageLogService;
import top.bootz.user.service.rabbit.BaseReceiver;

/**
 * @Description : Rabbit消息接收处理中心
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:01
 */

@Slf4j
@Component
@NoArgsConstructor
public class UpdateAuthReceiver extends BaseReceiver {

	@Autowired
	public UpdateAuthReceiver(RabbitMessageLogService rabbitMessageLogService) {
		super(rabbitMessageLogService);
	}

	@RabbitListener(returnExceptions = "true", bindings = @QueueBinding(value = @Queue(value = RabbitConstants.Queue.UC_UPDATE_AUTH, autoDelete = "false", durable = "true"), 
			exchange = @Exchange(value = RabbitConstants.Exchange.DIRECT, type = ExchangeTypes.DIRECT, autoDelete = "false", durable = "true"), 
			key = RabbitConstants.RoutingKey.UC_DIRECT_UPDATE_AUTH))
	public void onMessage(@Payload BaseMessage message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
			Channel channel) {
		try {
			if (message == null) {
				log.error("The received message is illegal. {}", message);
				channel.basicNack(deliveryTag, false, false);
				return;
			}

			log.debug("监听处理消息。exchange [" + RabbitConstants.Exchange.DIRECT + "] queue ["
					+ RabbitConstants.Queue.UC_UPDATE_AUTH + "]");

			afterRecivedMessage(message);

			// 确认接收处理消息成功
			channel.basicAck(deliveryTag, false);
		} catch (Exception e) {
			log.error("An unknown error has occurred when handle message. exchange [" + RabbitConstants.Exchange.DIRECT
					+ "] queue [" + RabbitConstants.Queue.UC_UPDATE_AUTH + "]", e);
			try {
				channel.basicNack(deliveryTag, false, false);
			} catch (IOException e1) {
				log.error("basic nack message fail.", e1);
			}
		}
	}

}
