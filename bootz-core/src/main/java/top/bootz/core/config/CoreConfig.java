package top.bootz.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import top.bootz.commons.generator.IdGenerator;

@Configuration
@ComponentScan(basePackages = { "top.bootz.core" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
public class CoreConfig {

	/**
	 * 分布式Id生成器
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public IdGenerator idGenerator() {
		return new IdGenerator();
	}

}
