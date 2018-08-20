package top.bootz.security.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.User;
import top.bootz.security.web.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User findByUserName(String userName) {
		return this.userRepository.findByUsername(userName);
	}

}
