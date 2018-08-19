package top.bootz.security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.security.demo.entity.User;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午10:53:55
 */

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
