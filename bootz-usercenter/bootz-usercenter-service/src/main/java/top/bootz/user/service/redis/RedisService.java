package top.bootz.user.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public <T> void setKey(String key, T value) {
		redisTemplate.opsForValue().set(key, value);
	}

}
