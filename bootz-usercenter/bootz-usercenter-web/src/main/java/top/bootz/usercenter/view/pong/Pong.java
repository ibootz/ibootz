package top.bootz.usercenter.view.pong;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 
 * @author John
 * @time 2018年6月18日 上午2:26:20
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pong extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private boolean mysql;

	private boolean redis;

	private boolean mongodb;

	private boolean elastic;

	private boolean rabbitmq;

}
