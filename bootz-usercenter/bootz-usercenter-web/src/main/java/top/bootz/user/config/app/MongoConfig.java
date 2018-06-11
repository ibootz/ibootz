package top.bootz.user.config.app;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@Configuration
@EnableMongoRepositories(basePackages = {"top.bootz.user.persist.mongo"})
@EnableMongoAuditing(auditorAwareRef = "mongoAuditorProvider", dateTimeProviderRef = "mongoDateTimeProvider", modifyOnCreate = false)
public class MongoConfig {

    /**
     * 添加操作时间审计
     *
     * @return DateTimeProvider
     */
    @Bean
    public DateTimeProvider mongoDateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }

    /**
     * 添加操作人审计 TODO 暂时没有实现登录授权功能,所以这里暂时没法获取当前用户
     */
    @Bean
    public AuditorAware<String> mongoAuditorProvider() {
        return new AuditorAware<String>() {

            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of("");
            }
        };
    }

    @Bean
    public MongoExceptionTranslator exceptionTranslator() {
        return new MongoExceptionTranslator();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingEventListener logginEventListener() {
        return new LoggingEventListener();
    }

}
