package top.bootz.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import top.bootz.user.config.app.AsyncConfig;
import top.bootz.user.config.app.IdGeneratorConfig;
import top.bootz.user.config.app.JpaConfig;
import top.bootz.user.properties.IdGeneratorProperties;
import top.bootz.user.properties.TaskThreadPoolConfigProperties;

@EnableRetry
@EnableCaching
@EnableScheduling
@Import(value = { AsyncConfig.class, JpaConfig.class, IdGeneratorConfig.class })
@EnableConfigurationProperties({ TaskThreadPoolConfigProperties.class, IdGeneratorProperties.class })
@SpringBootApplication
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
