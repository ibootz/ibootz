package top.bootz.user.entity.rabbit;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.dto.BaseMessage;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午8:26:23
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PingMessage extends BaseMessage {

	private static final long serialVersionUID = 1L;

	private String ping;

	public PingMessage(int from, int to, Long createtor, LocalDateTime createTime, String ping) {
		super(from, to, createtor, createTime);
		this.ping = ping;
	}

}
