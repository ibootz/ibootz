package top.bootz.user.service.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import top.bootz.user.entity.mongo.RabbitMessageLog;
import top.bootz.user.repository.mongo.RabbitMessageLogRepository;

/**
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-24 上午7:44
 */

@Service
public class RabbitMessageLogService {

	@Autowired
	private RabbitMessageLogRepository rabbitMessageLogRepository;

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
