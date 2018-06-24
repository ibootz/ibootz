package top.bootz.user.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import top.bootz.user.commons.constants.RabbitConstants;

/**
 * Rabbitmq 配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:13:43
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

	// @Autowired
	// private RabbitProperties properties;
	//
	// /** 配置消息生产者端的RabbitTemplate, 为了处理消息回调，这里设置为原型模式 */
	// @Bean
	// @Primary
	// @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, scopeName =
	// ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	// public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
	// MessageConverter jsonMessageConverter) {
	// PropertyMapper map = PropertyMapper.get();
	// RabbitTemplate template = new RabbitTemplate(connectionFactory);
	// template.setMessageConverter(jsonMessageConverter);
	// template.setMandatory(determineMandatoryFlag());
	// RabbitProperties.Template properties = this.properties.getTemplate();
	// if (properties.getRetry().isEnabled()) {
	// template.setRetryTemplate(createRetryTemplate(properties.getRetry()));
	// }
	// map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis).to(template::setReceiveTimeout);
	// map.from(properties::getReplyTimeout).whenNonNull().as(Duration::toMillis).to(template::setReplyTimeout);
	// map.from(properties::getExchange).to(template::setExchange);
	// map.from(properties::getRoutingKey).to(template::setRoutingKey);
	// return template;
	// }
	//
	// private boolean determineMandatoryFlag() {
	// Boolean mandatory = this.properties.getTemplate().getMandatory();
	// return (mandatory != null ? mandatory :
	// this.properties.isPublisherReturns());
	// }
	//
	// private RetryTemplate createRetryTemplate(RabbitProperties.Retry
	// properties) {
	// PropertyMapper map = PropertyMapper.get();
	// RetryTemplate template = new RetryTemplate();
	// SimpleRetryPolicy policy = new SimpleRetryPolicy();
	// map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
	// template.setRetryPolicy(policy);
	// ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
	// map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
	// .to(backOffPolicy::setInitialInterval);
	// map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
	// map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis).to(backOffPolicy::setMaxInterval);
	// template.setBackOffPolicy(backOffPolicy);
	// return template;
	// }
	//
	// @Bean
	// @Primary
	// @ConditionalOnMissingBean
	// public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
	// return new Jackson2JsonMessageConverter(objectMapper);
	// }
	//
	// @Bean
	// @Primary
	// @ConditionalOnMissingBean
	// public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
	// return new RabbitAdmin(connectionFactory);
	// }
	//
	// /** 配置消息消费者端监听器 */
	// @Bean
	// @Primary
	// @ConditionalOnMissingBean
	// public SimpleRabbitListenerContainerFactory
	// rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
	// MessageConverter jsonMessageConverter) {
	// SimpleRabbitListenerContainerFactory factory = new
	// SimpleRabbitListenerContainerFactory();
	// factory.setConnectionFactory(connectionFactory);
	// factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
	// factory.setMessageConverter(jsonMessageConverter);
	// return factory;
	// }

	@Bean
	@Primary
	public RabbitAdmin rabbitAdmin(CachingConnectionFactory rabbitConnectionFactory) {
		return new RabbitAdmin(rabbitConnectionFactory);
	}

	/**
	 * 配置Exchange
	 * 
	 * @author John <br/>
	 * @time 2018年6月24日 下午6:45:54
	 */

	/** routingKey必须要完全和Banding中定义的完全匹配，才会发送消息给相应的队列 */
	@Bean(name = "directExchange")
	@ConditionalOnMissingBean
	public DirectExchange directExchange() {
		return new DirectExchange(RabbitConstants.Exchange.DIRECT.getName(), true, false);
	}

	/** 支持通过指定包含通配符的routingKey同时将消息发送给多个绑定队列，达到发布/订阅的效果 */
	@Bean(name = "topicExchange")
	@ConditionalOnMissingBean
	public TopicExchange topicExchange() {
		return new TopicExchange(RabbitConstants.Exchange.TOPIC.getName(), true, false);
	}

	@Bean(name = "pingQueue")
	@ConditionalOnMissingBean
	public Queue pingQueue() {
		return new Queue(RabbitConstants.Queue.USERCENTER_PING.getName(), true, false, false);
	}

	@Bean(name = "authQueue")
	@ConditionalOnMissingBean
	public Queue authQueue() {
		return new Queue(RabbitConstants.Queue.USERCENTER_UPDATE_AUTH.getName(), true, false, false);
	}

	/** 将队列pingQueue和directExchange绑定起来 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(name = { "pingQueue", "rabbitAdmin" })
	public Binding bindPingQueueToDirectExchange(@Qualifier("pingQueue") Queue queue,
			@Qualifier("directExchange") DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(RabbitConstants
				.buildRoutingKey(RabbitConstants.Exchange.DIRECT, RabbitConstants.Queue.USERCENTER_PING));
		binding.setAdminsThatShouldDeclare(rabbitAdmin);
		return binding;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(name = { "authQueue", "rabbitAdmin" })
	public Binding bindAuthQueueToDirectExchange(@Qualifier("authQueue") Queue queue,
			@Qualifier("directExchange") DirectExchange exchange, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(RabbitConstants
				.buildRoutingKey(RabbitConstants.Exchange.DIRECT, RabbitConstants.Queue.USERCENTER_UPDATE_AUTH));
		binding.setAdminsThatShouldDeclare();
		return binding;
	}

	/** 将队列createUserQueue和topicExchange绑定起来 */
	// @Bean
	// @ConditionalOnMissingBean
	// @ConditionalOnBean(name = "createUserQueue")
	// public Binding bindUserCreateQueueToDirectExchange() {
	// return
	// BindingBuilder.bind(createUserQueue()).to(directExchange()).with(RabbitConstants
	// .buildRoutingKey(RabbitConstants.Exchange.DIRECT,
	// RabbitConstants.Queue.USERCENTER_CREATE_USER));
	// }

	@Bean(value = "messageListenerContainer")
	@ConditionalOnBean(name = { "rabbitAdmin" })
	public SimpleMessageListenerContainer messageListenerContainer(CachingConnectionFactory rabbitConnectionFactory,
			RabbitAdmin rabbitAdmin) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(rabbitConnectionFactory);
		container.setRabbitAdmin(rabbitAdmin);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return container;
	}

}
