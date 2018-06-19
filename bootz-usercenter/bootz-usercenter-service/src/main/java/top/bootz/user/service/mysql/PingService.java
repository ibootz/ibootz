package top.bootz.user.service.mysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.user.entity.mysql.ping.Ping;
import top.bootz.user.repository.mysql.ping.PingRepository;

/**
 * 
 * @author John
 * @time 2018年6月18日 上午1:21:25
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PingService {

	@Autowired
	private PingRepository pingRepository;

	@Transactional(readOnly = false)
	public Ping save(Ping ping) {
		return pingRepository.save(ping);
	}

	@Async
	@Transactional(readOnly = false)
	public void asyncSave(Ping ping) {
		save(ping);
	}

	@Transactional(readOnly = false)
	public void delete(Ping ping) {
		pingRepository.delete(ping);
	}

	@Async
	@Transactional(readOnly = false)
	public void asyncDelete(Ping ping) {
		delete(ping);
	}

	public Optional<Ping> find(Long id) {
		return pingRepository.findById(id);
	}

}
