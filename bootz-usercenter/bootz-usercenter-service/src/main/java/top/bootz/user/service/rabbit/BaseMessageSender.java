package top.bootz.user.service.rabbit;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import com.google.common.base.Preconditions;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.snowflake.IdGenerator;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.user.entity.mongo.RabbitMessageLog;
import top.bootz.user.service.mongo.RabbitMessageLogService;

/**
 * 发送消息的基础通用类
 * 
 * @author John
 * @time 2018年6月24日 下午8:03:43
 */

@Slf4j
@NoArgsConstructor
public class BaseMessageSender {

	private RabbitMessageLogService rabbitMessageLogService;

	private RabbitTemplate rabbitTemplate;

	private IdGenerator idGenerator;

	/** 由子类注入 */
	protected BaseMessageSender(RabbitMessageLogService rabbitMessageLogService, IdGenerator idGenerator,
			RabbitTemplate rabbitTemplate) {
		this.rabbitMessageLogService = rabbitMessageLogService;
		this.idGenerator = idGenerator;
		this.rabbitTemplate = rabbitTemplate;
		setupCallbacks();
	}

	/**
	 * 发送消息的主要方法
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @param messageHeaders
	 * @return
	 * @Author : Zhangq <momogoing@163.com>
	 * @CreationDate : 2018年6月28日 上午12:10:02
	 */
	public Long send(String exchange, String routingKey, BaseMessage message, Map<String, Object> messageHeaders) {
		Preconditions.checkArgument(StringUtils.isNotBlank(exchange), "Rabbit exchange must not be blank");
		Preconditions.checkArgument(StringUtils.isNotBlank(routingKey), "Rabbit routing key must not be blank");
		Preconditions.checkArgument(message != null, "Rabbit message payload must not be blank");

		// 手动设置消息唯一标识
		Long messageId = message.getId();
		if (messageId == null || messageId == 0L) {
			messageId = idGenerator.nextId();
		}
		message.setId(messageId);

		boolean isSent = true;
		try {
			CorrelationData correlationData = new CorrelationData(String.valueOf(messageId));
			rabbitTemplate.convertAndSend(exchange, routingKey, message, new CustomMessagePostProcessor(messageHeaders),
					correlationData);
		} catch (Exception e) {
			log.error("An exception has occurred when sending rabbit message. exchange: " + exchange + ", routingKey: "
					+ routingKey + ", message: " + message.toJson(), e);
			isSent = false;
		}

		RabbitMessageLog messageLog = new RabbitMessageLog(exchange, routingKey, message, isSent);
		rabbitMessageLogService.asyncSave(messageLog);

		return messageId;
	}

	/** 消息后处理器 */
	protected final class CustomMessagePostProcessor implements MessagePostProcessor {

		private Map<String, Object> messageHeaders;

		CustomMessagePostProcessor(Map<String, Object> messageHeaders) {
			this.messageHeaders = messageHeaders;
		}

		@Override
		public Message postProcessMessage(Message msg) throws AmqpException {
			MessageProperties properties = msg.getMessageProperties();
			properties.setContentEncoding(rabbitTemplate.getEncoding());
			properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
			if (messageHeaders != null) {
				for (Map.Entry<String, Object> entry : messageHeaders.entrySet()) {
					properties.setHeader(entry.getKey(), entry.getValue());
				}
			}
			return msg;
		}
	}

	protected void setupCallbacks() {
		// broker服务器确认消息已递送到指定队列(此时消息已进入队列，但不代表消费者已经成功消费。消费者端手动确认与此处发送端的确认回调不是一码事)的回调
		if (!rabbitTemplate.isConfirmListener()) {
			this.rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
				String messageId = correlationData.getId();
				log.debug("message is confirmed, messageId: {}, ack: {}, cause: {}", messageId, ack, cause);
				Optional<RabbitMessageLog> opt = rabbitMessageLogService.findByMessageId(Long.valueOf(messageId));
				if (opt.isPresent()) {
					RabbitMessageLog messageLog = opt.get();
					messageLog.setConfirmed(ack && !messageLog.getReturned());
					messageLog.setCause(messageLog.getReturned() ? messageLog.getCause() : cause);
					rabbitMessageLogService.asyncSave(messageLog);
				};
			});
		}

		// broker退回消息回调(当指定的exchange存在但是通过的routingkey找不到对应的队列时，broker会将消息退回来)
		this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.debug(
					"message is returned callback, messageId:{}; replyCode:{}; replyText:{}; exchange:{}; routingKey:{}",
					message, replyCode, replyText, exchange, routingKey);
			String messageId = message.getMessageProperties().getCorrelationId();
			if (StringUtils.isBlank(messageId)) {
				messageId = message.getMessageProperties().getMessageId();
			}
			Optional<RabbitMessageLog> opt = rabbitMessageLogService.findByMessageId(Long.valueOf(messageId));
			opt.ifPresent(messageLog -> {
				messageLog.setReturned(Boolean.TRUE);
				messageLog.setConfirmed(Boolean.FALSE);
				messageLog.setReceived(Boolean.FALSE);
				messageLog.setCause(replyCode + ":" + replyText);
				rabbitMessageLogService.asyncSave(messageLog);
			});
		});
	}

}
