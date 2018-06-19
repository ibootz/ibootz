package top.bootz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import top.bootz.commons.helper.SpringHelper;
import top.bootz.usercenter.config.AsyncConfig;
import top.bootz.usercenter.config.CacheConfig;
import top.bootz.usercenter.config.IdGeneratorConfig;
import top.bootz.usercenter.config.JpaConfig;
import top.bootz.usercenter.config.MongoConfig;
import top.bootz.usercenter.config.RabbitmqConfig;
import top.bootz.usercenter.config.RedisConfig;
import top.bootz.usercenter.config.properties.IdGeneratorProperties;
import top.bootz.usercenter.config.properties.TaskThreadPoolConfigProperties;

@EnableRetry
@EnableWebMvc
@EnableScheduling
@EnableSpringDataWebSupport
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableConfigurationProperties({ IdGeneratorProperties.class, TaskThreadPoolConfigProperties.class })
@Import(value = { AsyncConfig.class, CacheConfig.class, IdGeneratorConfig.class, JpaConfig.class, MongoConfig.class,
		RabbitmqConfig.class, RedisConfig.class })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		SpringHelper.setApplicationContext(applicationContext);
	}

}
