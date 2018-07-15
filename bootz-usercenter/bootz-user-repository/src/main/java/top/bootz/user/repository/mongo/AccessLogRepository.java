package top.bootz.user.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import top.bootz.user.entity.mongo.AccessLog;

/**
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午7:47
 */
public interface AccessLogRepository extends MongoRepository<AccessLog, ObjectId> {
}
