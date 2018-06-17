package top.bootz.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * 
 * @author John
 * @time 2018年6月17日 上午10:48:00
 */

@Configuration
@EnableRedisRepositories(basePackages = { "top.bootz.user.persist.redis" })
public class RedisConfig {

}
