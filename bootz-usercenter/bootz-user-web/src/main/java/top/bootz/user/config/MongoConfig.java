package top.bootz.user.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Spring data mongodb 配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:13:22
 */
@Configuration
@EnableMongoRepositories(basePackages = { "top.bootz.user.repository.mongo" })
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
