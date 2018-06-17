package top.bootz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import top.bootz.commons.helper.SpringHelper;
import top.bootz.user.config.AsyncConfig;
import top.bootz.user.config.CorsConfig;
import top.bootz.user.config.IdGeneratorConfig;
import top.bootz.user.config.JpaConfig;
import top.bootz.user.config.properties.CorsConfigProperties;
import top.bootz.user.config.properties.IdGeneratorProperties;
import top.bootz.user.config.properties.TaskThreadPoolConfigProperties;

@EnableRetry
@EnableWebMvc
@EnableCaching
@EnableScheduling
@EnableSpringDataWebSupport
@Import(value = { AsyncConfig.class, CorsConfig.class, IdGeneratorConfig.class, JpaConfig.class })
@EnableConfigurationProperties({ TaskThreadPoolConfigProperties.class, IdGeneratorProperties.class,
		CorsConfigProperties.class })
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		SpringHelper.setApplicationContext(applicationContext);
	}

}
