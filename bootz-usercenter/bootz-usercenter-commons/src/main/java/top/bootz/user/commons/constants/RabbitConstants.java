package top.bootz.user.commons.constants;

/**
 * Rabbitmq相关的常量 <br/>
 * 
 * @author John <br/>
 *         2018年6月10日 下午5:25:05 <br/>
 */
public final class RabbitConstants {

	private RabbitConstants() {
	}

	public enum Exchange {

		DIRECT("app.direct"), // Direct类型的exchange：根据routingKey完全匹配queue

		TOPIC("app.topic"); // Topic类型的exchange：根据占位符一次可以匹配多个符合条件的queueu

		private String name;

		Exchange(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

	public enum Queue {

		USERCENTER_DEAD_LETTER("app.usercenter.dead_letter"), // 死信队列，用于盛放投递失败的消息

		USERCENTER_PING("app.usercenter.ping"), // 测试Rabbitmq状态

		USERCENTER_CREATE_USER("app.create_user"), // 发布创建用户事件

		USERCENTER_UPDATE_AUTH("app.update_auth"); // 发布更新授权事件

		private String name;

		Queue(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

	}

	/**
	 * 构建rabbit绑定路由
	 * 
	 * @param exchange
	 * @param queue
	 * @return
	 * @author John
	 * @time 2018年6月24日 下午4:32:58
	 */
	public static String buildRoutingKey(Exchange exchange, Queue queue) {
		return (exchange.name() + "." + queue.name()).toLowerCase();
	}

}
