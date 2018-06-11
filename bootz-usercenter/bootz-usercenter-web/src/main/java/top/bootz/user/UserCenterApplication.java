package top.bootz.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;
import top.bootz.user.config.app.JpaConfig;
import top.bootz.user.properties.MongoConfigProperties;
import top.bootz.user.properties.TaskThreadPoolConfigProperties;

@Slf4j
@EnableRetry
@EnableCaching
@EnableScheduling
@SpringBootApplication
// @Import(value = { AsyncConfig.class, ElasticConfig.class, JpaConfig.class,
// MongoConfig.class, RabbitmqConfig.class })
@Import(value = { JpaConfig.class })
@EnableConfigurationProperties({ TaskThreadPoolConfigProperties.class, MongoConfigProperties.class })
public class UserCenterApplication {

	public static void main(String[] args) {
		log.debug("test logback log ... ");
		SpringApplication.run(UserCenterApplication.class, args);
	}

}
