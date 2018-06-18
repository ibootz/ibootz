package top.bootz.user.service.mysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.user.entity.mysql.user.User;
import top.bootz.user.repository.mysql.user.UserRepository;

/**
 * 
 * @author John 2018年6月11日 下午10:09:58
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = false)
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Async
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Async
	@Transactional(readOnly = false)
	public void asyncSave(User user) {
		userRepository.save(user);
	}

	public Optional<User> findUser(Long id) {
		return userRepository.findById(id);
	}

}
