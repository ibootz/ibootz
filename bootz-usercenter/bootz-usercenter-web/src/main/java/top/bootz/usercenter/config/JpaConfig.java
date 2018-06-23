package top.bootz.usercenter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
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
