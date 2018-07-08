package top.bootz.user.service.redis;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.bootz.user.entity.redis.PingCache;
import top.bootz.user.repository.redis.PingCacheRepository;

/**
 * 使用RedisRepository的方式操作redis数据库
 * 
 * @author John <br/>
 * @time 2018年6月19日 下午8:57:05
 */

@Service
public class PingCacheService {

    @Autowired
    private PingCacheRepository pingCacheRepository;

    public PingCache save(PingCache pingCache) {
        return pingCacheRepository.save(pingCache);
    }

    @Async
    public void asyncSave(PingCache pingCache) {
        save(pingCache);
    }

    public Optional<PingCache> find(String id) {
        return pingCacheRepository.findById(id);
    }

    public void delete(PingCache pingCache) {
        pingCacheRepository.delete(pingCache);
    }

    @Async
    public void asyncDelete(PingCache pingCache) {
        delete(pingCache);
    }

}
