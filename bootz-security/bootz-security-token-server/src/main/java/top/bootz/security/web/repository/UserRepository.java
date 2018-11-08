package top.bootz.security.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.security.web.entity.User;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午10:53:55
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByMobile(String mobile);

}
