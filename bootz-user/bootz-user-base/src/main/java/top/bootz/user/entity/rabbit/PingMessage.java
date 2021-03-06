package top.bootz.user.entity.rabbit;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import top.bootz.core.base.message.RabbitMessage;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午8:26:23
 */

@Setter
@Getter
public class PingMessage extends RabbitMessage {

    private static final long serialVersionUID = 1L;

    private String ping;

    public PingMessage(String from, String[] to, Long createtor, LocalDateTime createTime, String ping) {
        super(from, to, createtor, createTime);
        this.ping = ping;
    }

}
