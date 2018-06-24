package top.bootz.user.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import top.bootz.user.entity.mongo.RabbitMessageLog;

/**
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:41
 */
public interface RabbitMessageLogRepository extends MongoRepository<RabbitMessageLog, ObjectId> {

	Optional<RabbitMessageLog> findByMessage_Id(Long messageId);

}
