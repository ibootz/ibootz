package top.bootz.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
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
     * 添加操作人审计
     */
    @Bean
    public AuditorAware<Long> mongoAuditorProvider() {
        // TODO 暂时没有实现登录授权功能,所以这里暂时没法获取当前用户
        return () -> Optional.of(-1L);
    }

}
