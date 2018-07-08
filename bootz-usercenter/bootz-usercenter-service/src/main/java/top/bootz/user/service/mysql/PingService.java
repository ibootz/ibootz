package top.bootz.user.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.bootz.user.entity.mysql.ping.Ping;
import top.bootz.user.repository.mysql.ping.PingRepository;

import java.util.Optional;

/**
 * @author John
 * @time 2018年6月18日 上午1:21:25
 */

@Service
@CacheConfig(cacheNames = { "ping" })
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PingService {

    @Autowired
    private PingRepository pingRepository;

    @Transactional
    @CachePut(key = "'ping:'.concat(#ping.id)")
    public Ping save(Ping ping) {
        return pingRepository.save(ping);
    }

    @Async
    @Transactional
    @CachePut(key = "'ping:'.concat(#ping.id)")
    public void asyncSave(Ping ping) {
        save(ping);
    }

    @Transactional
    @CacheEvict(key = "'ping:'.concat(#ping.id)")
    public void delete(Ping ping) {
        pingRepository.delete(ping);
    }

    @Async
    @Transactional
    @CacheEvict(key = "'ping:'.concat(#ping.id)")
    public void asyncDelete(Ping ping) {
        delete(ping);
    }

    @Cacheable(key = "'ping:'.concat(#id)")
    public Optional<Ping> find(Long id) {
        System.out.println("find db");
        return pingRepository.findById(id);
    }

}
