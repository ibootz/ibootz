package top.bootz.user.service.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.bootz.user.entity.mongo.AccessLog;
import top.bootz.user.repository.mongo.AccessLogRepository;

import java.util.Optional;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.mongo
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:45
 */

@Service
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepostiory;

    public AccessLog save(AccessLog accessLog) {
        return accessLogRepostiory.save(accessLog);
    }

    @Async
    public void asyncSave(AccessLog accessLog) {
        save(accessLog);
    }

    public Optional<AccessLog> find(ObjectId objectId) {
        return accessLogRepostiory.findById(objectId);
    }

    public void delete(AccessLog accessLog) {
        accessLogRepostiory.delete(accessLog);
    }

    @Async
    public void asyncDelete(AccessLog accessLog) {
        delete(accessLog);
    }

}