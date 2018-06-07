package top.bootz.user.commons.constants;

/**
 * Rabbitmq相关的常量
 * @author John
 *
 */
public final class RabbitmqConstants {

	private RabbitmqConstants() {
		
	}
	
	/**
	 * pms子系统统一使用的channel代号
	 */
	public static final String EXCHANGE_PMS = "pms";
	
	/**
	 * 供订单模块使用的消息队列
	 */
	public static final String QUEUE_PMS_ORDER = "order";

	/**
	 * 路由:pms - order
	 */
	public static final String ROUTINGKEY_PMS_ORDER = "pms.order";
	
	/**
	 * 物流模块使用的消息队列
	 */
	public static final String QUEUE_PMS_LOGISTICS = "logistics";
	
	/**
	 * 路由:orion - pms
	 */
	public static final String ROUTINGKEY_PMS_LOGISTICS = "pms.logistics";
	
}
