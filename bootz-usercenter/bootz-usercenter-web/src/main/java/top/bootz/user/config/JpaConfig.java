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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "top.bootz.user.persist.mysql" })
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
	public AuditorAware<String> auditorProvider() {
		// TODO 暂未填充操作人的审计信息
		return new AuditorAware<String>() {

			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of("TODO");
			}

		};
	}

}
