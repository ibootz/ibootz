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
public class BaseMessageSender implements RabbitTemplate.ConfirmCallback {

	public static final String USERCENTER_DEAD_LETTER = "app.usercenter.dead_letter";

	private RabbitMessageLogService rabbitMessageLogService;

	private RabbitTemplate rabbitTemplate;

	private IdGenerator idGenerator;

	/** 由子类注入 */
	protected BaseMessageSender(RabbitMessageLogService rabbitMessageLogService, IdGenerator idGenerator,
			RabbitTemplate rabbitTemplate) {
		this.rabbitMessageLogService = rabbitMessageLogService;
		this.idGenerator = idGenerator;
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 处理消息回调
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String messageId = correlationData.getId();
		log.debug("message confirm callback, messageId: {}, ack: {}, cause: {}", messageId, ack, cause);
		Optional<RabbitMessageLog> opt = rabbitMessageLogService.findByMessageId(Long.valueOf(messageId));
		opt.ifPresent(messageLog -> {
			messageLog.setConfirmed(ack);
			messageLog.setCause(cause);
			rabbitMessageLogService.asyncSave(messageLog);
		});
	}

	protected void send(String exchange, String routingKey, BaseMessage message, Map<String, Object> messageHeaders) {
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
			rabbitTemplate.convertSendAndReceive(exchange, routingKey, message,
					new CustomMessagePostProcessor(messageHeaders), correlationData);
			isSent = true;
		} catch (Exception e) {
			log.error("An exception has occurred when sending rabbit message. exchange: " + exchange + ", routingKey: "
					+ routingKey + ", message: " + message.toJson(), e);
			isSent = false;
		}

		RabbitMessageLog messageLog = new RabbitMessageLog(exchange, routingKey, message, isSent);
		rabbitMessageLogService.asyncSave(messageLog);
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

}
