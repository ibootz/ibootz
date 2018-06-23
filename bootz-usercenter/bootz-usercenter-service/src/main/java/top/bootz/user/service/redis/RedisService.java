package top.bootz.user.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    /**
     * @param key
     * @param value
     * @param timeout 过期时间，单位：秒
     * @param <V>
     */
    @Async
    public <V> void opsForValue(String key, V value, long timeout) {
        if (timeout == -1) {
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param values
     * @param timeout 过期时间，单位：秒
     * @param <HK>
     * @param <V>
     */
    @Async
    public <HK, V> void opsForHash(String key, Map<HK, V> values, long timeout) {
        if (timeout == -1) {
            redisTemplate.opsForValue().set(key, values);
        }
        redisTemplate.opsForValue().set(key, values, timeout, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param values
     * @param timeout 过期时间，单位：秒
     * @param <V>
     */
    @Async
    public <V> void opsForList(String key, List<V> values, long timeout) {
        if (timeout == -1) {
            redisTemplate.opsForValue().set(key, values);
        }
        redisTemplate.opsForList().leftPushAll(key, values, timeout, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @param values
     * @param timeout 过期时间，单位：秒
     * @param <V>
     */
    @Async
    public <V> void opsForSet(String key, Set<V> values, long timeout) {
        if (timeout == -1) {
            redisTemplate.opsForValue().set(key, values);
        }
        redisTemplate.opsForSet().add(key, values, timeout, TimeUnit.SECONDS);
    }

}
