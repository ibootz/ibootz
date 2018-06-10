package top.bootz.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import top.bootz.user.config.app.ElasticConfig;
import top.bootz.user.config.app.JpaConfig;
import top.bootz.user.config.app.MongoConfig;
import top.bootz.user.config.app.RabbitmqConfig;

@Slf4j
@EnableCaching
@SpringBootApplication
@Import(value = { JpaConfig.class, MongoConfig.class, RabbitmqConfig.class, ElasticConfig.class })
public class UserApplication {

	public static void main(String[] args) {
		log.debug("test logback log ... ");
		SpringApplication.run(UserApplication.class, args);
	}

}
