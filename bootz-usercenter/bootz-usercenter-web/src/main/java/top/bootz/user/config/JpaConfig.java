package top.bootz.user.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring data jpa 配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:13:06
 */

@Configuration
@EnableJpaRepositories(basePackages = { "top.bootz.user.repository.mysql" })
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class JpaConfig {

    /**
     * 添加操作时间审计
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DateTimeProvider dateTimeProvider() {
        return CurrentDateTimeProvider.INSTANCE;
    }

    /**
     * 添加操作人审计
     */
    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<Long> auditorProvider() {
        // TODO 暂未填充操作人的审计信息
        return () -> Optional.of(-1L);
    }

}
