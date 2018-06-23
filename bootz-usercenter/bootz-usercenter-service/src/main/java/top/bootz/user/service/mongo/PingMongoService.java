package top.bootz.user.service.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.bootz.user.entity.mongo.PingMongo;
import top.bootz.user.repository.mongo.PingMongoRepostiory;

import java.util.Optional;

/**
 * @author John
 * @time 2018年6月18日 下午2:42:28
 */

@Service
public class PingMongoService {

    @Autowired
    private PingMongoRepostiory pingMongoRepostiory;

    public PingMongo save(PingMongo pingMongo) {
        return pingMongoRepostiory.save(pingMongo);
    }

    @Async
    public void asyncSave(PingMongo pingMongo) {
        save(pingMongo);
    }

    public Optional<PingMongo> find(ObjectId objectId) {
        return pingMongoRepostiory.findById(objectId);
    }

    public void delete(PingMongo pingMongo) {
        pingMongoRepostiory.delete(pingMongo);
    }

    @Async
    public void asyncDelete(PingMongo pingMongo) {
        delete(pingMongo);
    }

}
