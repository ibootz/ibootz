package top.bootz.user.commons.constants;

/**
 * Rabbitmq相关的常量 <br/>
 * 
 * @author John <br/>
 *         2018年6月10日 下午5:25:05 <br/>
 */
public final class RabbitConstants {

	/** EXCHANGE */
	public static final class Exchange {

		// Direct类型的exchange：根据routingKey完全匹配queue
		public static final String DIRECT = "bootz.uc.direct";

		// Topic类型的exchange：根据占位符一次可以匹配多个符合条件的queueu
		public static final String TOPIC = "bootz.uc.topic";

		private Exchange() {
		}

	}

	/** QUEUE */
	public static final class Queue {

		// 发布测试事件的队列
		public static final String UC_PING = "app.uc.ping";

		// 发布创建用户事件的队列
		public static final String UC_CREATE_USER = "app.uc.createUser";

		// 发布更新授权事件的队列
		public static final String UC_UPDATE_AUTH = "app.uc.updateAuth";

		// 死信队列，用于盛放投递失败的消息
		public static final String UC_DEAD_LETTER = "app.uc.deadLetter";

		private Queue() {
		}

	}

	/** ROUTING-KEY */
	public static final class RoutingKey {

		// 直连-ping
		public static final String UC_DIRECT_PING = "app.uc.direct-ping";

		// 订阅-创建用户
		public static final String UC_TOPIC_CREATE_USER = "app.uc.topic-createUser";

		// 直连-更新授权
		public static final String UC_DIRECT_UPDATE_AUTH = "app.uc.direct-updateAuth";

		// 直连-死信队列
		public static final String UC_DIRECT_DEAD_LETTER = "app.uc.direct-deadLetter";

		private RoutingKey() {
		}

	}

	private RabbitConstants() {
	}

}
