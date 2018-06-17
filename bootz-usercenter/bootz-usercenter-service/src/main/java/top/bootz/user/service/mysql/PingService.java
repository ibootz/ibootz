package top.bootz.user.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void find(Long id) {
		pingRepository.findById(id);
	}

	@Transactional(readOnly = false)
	public void delete(Ping ping) {
		pingRepository.delete(ping);
	}

}
