package top.bootz.user.repository.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.bootz.user.entity.mongo.PingMongo;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.repository.mongo
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午8:31
 */


public interface PingMongoRepostiory extends MongoRepository<PingMongo, ObjectId> {
    
}
