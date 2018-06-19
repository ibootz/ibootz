package top.bootz.user.service.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 通用RedisTemplate操作redis数据
 * 
 * @author John
 * @time 2018年6月19日 下午8:55:28
 */

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Async
	public <V> void opsForValue(String key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Async
	public <HK, V> void opsForHash(String key, Map<HK, V> values) {
		redisTemplate.opsForHash().putAll(key, values);
	}

	@Async
	public <V> void opsForList(String key, List<V> values) {
		redisTemplate.opsForList().leftPushAll(key, values);
	}

	@Async
	public <V> void opsForSet(String key, Set<V> values) {
		redisTemplate.opsForSet().add(key, values);
	}

}
