package top.bootz.user.repository.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import top.bootz.user.entity.redis.PingCache;

/**
 * 
 * @author John
 * @time 2018年6月19日 下午8:48:07
 */

public interface PingCacheRepository extends KeyValueRepository<PingCache, String> {

}
