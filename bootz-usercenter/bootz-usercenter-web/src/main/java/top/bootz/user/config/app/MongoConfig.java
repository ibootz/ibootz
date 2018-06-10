package top.bootz.user.config.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import top.bootz.commons.converter.DateToLocalDateConverter;
import top.bootz.commons.converter.DateToLocalDateTimeConverter;
import top.bootz.commons.converter.DateToTimestampConverter;
import top.bootz.commons.converter.LocalDateTimeToDateConverter;
import top.bootz.commons.converter.LocalDateToDateConverter;

@Configuration
@EnableMongoRepositories(basePackages = { "top.bootz.user.biz.persist.mongo" })
@EnableMongoAuditing(auditorAwareRef = "mongoAuditorProvider", dateTimeProviderRef = "mongoDateTimeProvider", modifyOnCreate = false)
public class MongoConfig {

	/**
	 * 自定义转换器，处理date到timestamp的转换问题
	 */
	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(DateToTimestampConverter.INSTANCE);
		converters.add(DateToLocalDateConverter.INSTANCE);
		converters.add(DateToLocalDateTimeConverter.INSTANCE);
		converters.add(LocalDateTimeToDateConverter.INSTANCE);
		converters.add(LocalDateToDateConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}

	/**
	 * 添加操作时间审计
	 * 
	 * @return
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
	public LoggingEventListener logginEventListener() {
		return new LoggingEventListener();
	}

}
