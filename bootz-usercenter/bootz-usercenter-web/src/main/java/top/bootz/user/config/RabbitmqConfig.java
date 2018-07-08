package top.bootz.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import top.bootz.user.commons.constants.RabbitConstants;

import java.time.Duration;

/**
 * Rabbitmq 配置
 *
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:13:43
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    @Bean
    @Primary
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Configuration
    protected static class RabbitTemplateConfiguration {

        private final ObjectProvider<MessageConverter> messageConverter;

        private final RabbitProperties properties;

        public RabbitTemplateConfiguration(ObjectProvider<MessageConverter> messageConverter,
                RabbitProperties properties) {
            this.messageConverter = messageConverter;
            this.properties = properties;
        }

        @Bean
        @Primary
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
            PropertyMapper map = PropertyMapper.get();
            RabbitTemplate template = new RabbitTemplate(connectionFactory);
            MessageConverter messageConverter = this.messageConverter.getIfUnique();
            if (messageConverter != null) {
                template.setMessageConverter(messageConverter);
            }
            template.setMandatory(determineMandatoryFlag());
            RabbitProperties.Template properties = this.properties.getTemplate();
            if (properties.getRetry().isEnabled()) {
                template.setRetryTemplate(createRetryTemplate(properties.getRetry()));
            }
            map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
                    .to(template::setReceiveTimeout);
            map.from(properties::getReplyTimeout).whenNonNull().as(Duration::toMillis).to(template::setReplyTimeout);
            map.from(properties::getExchange).to(template::setExchange);
            map.from(properties::getRoutingKey).to(template::setRoutingKey);
            return template;
        }

        private boolean determineMandatoryFlag() {
            Boolean mandatory = this.properties.getTemplate().getMandatory();
            return (mandatory != null ? mandatory : this.properties.isPublisherReturns());
        }

        private RetryTemplate createRetryTemplate(RabbitProperties.Retry properties) {
            PropertyMapper map = PropertyMapper.get();
            RetryTemplate template = new RetryTemplate();
            SimpleRetryPolicy policy = new SimpleRetryPolicy();
            map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
            template.setRetryPolicy(policy);
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
                    .to(backOffPolicy::setInitialInterval);
            map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
            map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis).to(backOffPolicy::setMaxInterval);
            template.setBackOffPolicy(backOffPolicy);
            return template;
        }

        @Bean
        @ConditionalOnSingleCandidate(ConnectionFactory.class)
        @ConditionalOnProperty(prefix = "spring.rabbitmq", name = "dynamic", matchIfMissing = true)
        @ConditionalOnMissingBean
        public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
            return new RabbitAdmin(connectionFactory);
        }

    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstants.Exchange.DIRECT, true, false);
    }

    @Bean
    public Queue pingQueue() {
        return new Queue(RabbitConstants.Queue.UC_PING, true, false, false);
    }

    @Bean
    public Binding bindPingQueueToDirectExchange(@Qualifier("pingQueue") Queue pingQueue,
            @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(pingQueue).to(directExchange).with(RabbitConstants.RoutingKey.UC_DIRECT_PING);
    }

    @Bean
    public Queue updateAuthQueue() {
        return new Queue(RabbitConstants.Queue.UC_UPDATE_AUTH, true, false, false);
    }

    @Bean
    public Binding bindUpdateAuthQueueToDirectExchange(@Qualifier("updateAuthQueue") Queue updateAuthQueue,
            @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(updateAuthQueue).to(directExchange)
                .with(RabbitConstants.RoutingKey.UC_DIRECT_UPDATE_AUTH);
    }

}
