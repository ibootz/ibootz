package top.bootz.user.service.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.bootz.user.entity.mongo.RabbitSendMessageLog;
import top.bootz.user.repository.mongo.RabbitSendMessageLogRepository;

import java.util.Optional;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.mongo
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:44
 */

@Service
public class RabbitSendMessageLogService {

    @Autowired
    private RabbitSendMessageLogRepository rabbitSendMessageLogRepostiory;

    public RabbitSendMessageLog save(RabbitSendMessageLog rabbitSendMessageLog) {
        return rabbitSendMessageLogRepostiory.save(rabbitSendMessageLog);
    }

    @Async
    public void asyncSave(RabbitSendMessageLog rabbitSendMessageLog) {
        save(rabbitSendMessageLog);
    }

    public Optional<RabbitSendMessageLog> find(ObjectId objectId) {
        return rabbitSendMessageLogRepostiory.findById(objectId);
    }

    public void delete(RabbitSendMessageLog rabbitSendMessageLog) {
        rabbitSendMessageLogRepostiory.delete(rabbitSendMessageLog);
    }

    @Async
    public void asyncDelete(RabbitSendMessageLog rabbitSendMessageLog) {
        delete(rabbitSendMessageLog);
    }

}
