package top.bootz.user.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.bootz.user.entity.mongo.RabbitSendMessageLog;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.repository.mongo
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:41
 */
public interface RabbitSendMessageLogRepository extends MongoRepository<RabbitSendMessageLog, ObjectId> {
}
