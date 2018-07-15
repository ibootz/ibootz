package top.bootz.user.service.mongo;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.bootz.core.base.service.BaseMongoService;
import top.bootz.user.entity.mongo.PingMongo;
import top.bootz.user.entity.rabbit.PingMessage;
import top.bootz.user.repository.mongo.PingMongoRepository;

import java.util.Optional;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:25:20
 */

@Service
@NoArgsConstructor
public class PingMongoService extends BaseMongoService<PingMessage> {

    @Autowired
    private PingMongoRepository pingMongoRepository;

    /**
     * 该构造方法用于为baseMongoService通用父类注入mongodbTemplate工具类，必不可少。
     *
     * @param mongoTemplate
     */
    @Autowired
    private PingMongoService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public PingMongo save(PingMongo pingMongo) {
        return pingMongoRepository.save(pingMongo);
    }

    @Async
    public void asyncSave(PingMongo pingMongo) {
        save(pingMongo);
    }

    public Optional<PingMongo> find(ObjectId objectId) {
        return pingMongoRepository.findById(objectId);
    }

    public void delete(PingMongo pingMongo) {
        pingMongoRepository.delete(pingMongo);
    }

    @Async
    public void asyncDelete(PingMongo pingMongo) {
        delete(pingMongo);
    }

}
