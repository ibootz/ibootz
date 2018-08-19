package top.bootz.core.log;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = { "top.bootz.core.log" })
public class LogConfig {

}
