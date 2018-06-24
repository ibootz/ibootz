package top.bootz.user.service.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.bootz.user.entity.mongo.PingMongo;
import top.bootz.user.repository.mongo.PingMongoRepository;

/**
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:25:20
 */

@Service
public class PingMongoService {

	@Autowired
	private PingMongoRepository pingMongoRepository;

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
