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
import top.bootz.user.config.AsyncConfig;
import top.bootz.user.config.CacheConfig;
import top.bootz.user.config.IdGeneratorConfig;
import top.bootz.user.config.JpaConfig;
import top.bootz.user.config.MongoConfig;
import top.bootz.user.config.RabbitmqConfig;
import top.bootz.user.config.RedisConfig;
import top.bootz.user.config.properties.IdGeneratorProperties;
import top.bootz.user.config.properties.TaskThreadPoolConfigProperties;

@EnableWebMvc
@EnableScheduling
@EnableSpringDataWebSupport
@EnableRetry(proxyTargetClass = true)
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableConfigurationProperties({ IdGeneratorProperties.class, TaskThreadPoolConfigProperties.class })
@Import(value = { AsyncConfig.class, CacheConfig.class, IdGeneratorConfig.class, JpaConfig.class, MongoConfig.class,
		RabbitmqConfig.class, RedisConfig.class })
@SpringBootApplication(scanBasePackages = { "top.bootz" })
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		SpringHelper.setApplicationContext(applicationContext);
	}

}
