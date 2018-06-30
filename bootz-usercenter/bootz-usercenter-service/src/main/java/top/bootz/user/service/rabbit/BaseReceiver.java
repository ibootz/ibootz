package top.bootz.user.service.rabbit;

import lombok.NoArgsConstructor;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.user.entity.mongo.RabbitMessageLog;
import top.bootz.user.service.mongo.RabbitMessageLogService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月28日 上午12:17:27
 */

@NoArgsConstructor
public class BaseReceiver {

    private RabbitMessageLogService rabbitMessageLogService;

    public BaseReceiver(RabbitMessageLogService rabbitMessageLogService) {
        this.rabbitMessageLogService = rabbitMessageLogService;
    }

    // 接收到消息之后，处理日志相关部分
    protected void afterRecivedMessage(BaseMessage message) {
        Optional<RabbitMessageLog> rabbitMessageLogOpt = rabbitMessageLogService.findByMessageId(message.getId());
        rabbitMessageLogOpt.ifPresent(messageLog -> {
            // 由于消费者已经接收到消息，所以前一阶段的确认状态也可以置为true，错误消息置空，整个消息流程就算是走完了
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("received", Boolean.TRUE);
            updateMap.put("receivedTime", LocalDateTime.now());
            rabbitMessageLogService.updateFiled(messageLog.getId(), updateMap);
        });
    }

}
