package top.bootz.core.log;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import top.bootz.core.base.service.BaseMongoService;

/**
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:45
 */

@Service
@NoArgsConstructor
public class AccessLogService extends BaseMongoService<AccessLog> {

    @Autowired
    private AccessLogRepository accessLogRepository;

    /**
     * 该构造方法用于为baseMongoService通用父类注入mongodbTemplate工具类，必不可少。
     *
     * @param mongoTemplate
     */
    @Autowired
    private AccessLogService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public AccessLog save(AccessLog accessLog) {
        return accessLogRepository.save(accessLog);
    }

    @Async
    public void asyncSave(AccessLog accessLog) {
        save(accessLog);
    }

    public Optional<AccessLog> find(ObjectId objectId) {
        return accessLogRepository.findById(objectId);
    }

    public void delete(AccessLog accessLog) {
        accessLogRepository.delete(accessLog);
    }

    @Async
    public void asyncDelete(AccessLog accessLog) {
        delete(accessLog);
    }

}