package top.bootz.user.repository.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.bootz.user.entity.mysql.user.User;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
