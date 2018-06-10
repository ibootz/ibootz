package top.bootz.user.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

import top.bootz.user.config.subconfig.ElasticConfig;
import top.bootz.user.config.subconfig.JpaConfig;
import top.bootz.user.config.subconfig.MongoConfig;
import top.bootz.user.config.subconfig.RabbitmqConfig;

@Configuration
@Import(value = { JpaConfig.class, MongoConfig.class, RabbitmqConfig.class, ElasticConfig.class })
@ComponentScan(basePackages = { "top.bootz.user" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class }) })
@EnableCaching
public class AppConfig {

}
