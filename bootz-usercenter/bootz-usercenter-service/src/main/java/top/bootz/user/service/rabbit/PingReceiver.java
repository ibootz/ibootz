package top.bootz.user.service.rabbit;

import java.util.Map;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.StringHelper;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.user.entity.mongo.RabbitMessageLog;
import top.bootz.user.service.mongo.RabbitMessageLogService;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.rabbit.sender
 * @Description : Rabbit消息接收处理中心
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午11:01
 */

@Slf4j
@Component
@RabbitListener(queues = "app.usercenter.ping", admin = "rabbitAdmin")
public class PingReceiver {

	/** 监听队列名务必于<a>RabbitConstants.Queue</a>中的字段一致 */
	public static final String USERCENTER_PING = "app.usercenter.ping";

	@Autowired
	private RabbitMessageLogService rabbitMessageLogService;

	@RabbitHandler
	public void pingListener(@Payload BaseMessage message, @Headers Map<String, Object> messageHeaders) {
		if (message == null) {
			log.error("The received message is illegal. {}", JsonHelper.toJSON(message));
			return;
		}

		// 接收到消息之后做一些处理 ...
		log.debug("监听到[{}]队列的消息: {}", USERCENTER_PING, message.toJson());

		afterRecivedMessage(message);
	}

	// 接收到消息之后，处理日志相关部分
	private void afterRecivedMessage(BaseMessage message) {
		Optional<RabbitMessageLog> rabbitMessageLogOpt = rabbitMessageLogService.findByMessageId(message.getId());
		rabbitMessageLogOpt.ifPresent(messageLog -> {
			messageLog.setReceived(Boolean.TRUE);
			// 由于消费者已经接收到消息，所以前一阶段的确认状态也可以置为true，错误消息置空，整个消息流程就算是走完了
			messageLog.setConfirmed(Boolean.TRUE);
			messageLog.setCause(StringHelper.EMPTY);
			rabbitMessageLogService.asyncSave(messageLog);
		});
	}

}
