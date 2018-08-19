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
 * @CreationDate : 2018-06-24 上午7:44
 */

@Service
@NoArgsConstructor
public class RabbitMessageLogService extends BaseMongoService<RabbitMessageLog> {

    @Autowired
    private RabbitMessageLogRepository rabbitMessageLogRepository;

    /**
     * 该构造方法用于为baseMongoService通用父类注入mongodbTemplate工具类，必不可少。
     *
     * @param mongoTemplate
     */
    @Autowired
    private RabbitMessageLogService(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public RabbitMessageLog save(RabbitMessageLog rabbitMessageLog) {
        return rabbitMessageLogRepository.save(rabbitMessageLog);
    }

    @Async
    public void asyncSave(RabbitMessageLog rabbitMessageLog) {
        save(rabbitMessageLog);
    }

    public Optional<RabbitMessageLog> find(ObjectId objectId) {
        return rabbitMessageLogRepository.findById(objectId);
    }

    public void delete(RabbitMessageLog rabbitMessageLog) {
        rabbitMessageLogRepository.delete(rabbitMessageLog);
    }

    @Async
    public void asyncDelete(RabbitMessageLog rabbitMessageLog) {
        delete(rabbitMessageLog);
    }

    public Optional<RabbitMessageLog> findByMessageId(Long messageId) {
        return rabbitMessageLogRepository.findByMessage_Id(messageId);
    }

}
