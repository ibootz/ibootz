package top.bootz.user.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
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

	public User saveUser(User user) {
		return userRepository.save(user);
	}

}
