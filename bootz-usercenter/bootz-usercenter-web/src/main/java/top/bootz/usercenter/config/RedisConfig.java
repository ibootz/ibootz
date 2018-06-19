package top.bootz.usercenter.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * @author John
 * @time 2018年6月19日 下午10:12:41
 */

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

	/**
	 * 自定义RedisTemplete,
	 * 采用json序列化机制替换掉默认的jdk序列化，提高redis中value的可读性，同时也能减少value的大小
	 * 
	 * @param redisConnectionFactory
	 * @return
	 * @author John
	 * @time 2018年6月19日 下午10:21:11
	 */
	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(genericJackson2JsonRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(genericJackson2JsonRedisSerializer);
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

}
