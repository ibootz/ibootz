package top.bootz.user.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.bootz.user.entity.mysql.user.User;
import top.bootz.user.repository.mysql.user.UserDao;

/**
 * 
 * @author John
 * 2018年6月11日 下午10:09:58
 */

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void saveUser(User user) {
		userDao.save(user);
	}

}
