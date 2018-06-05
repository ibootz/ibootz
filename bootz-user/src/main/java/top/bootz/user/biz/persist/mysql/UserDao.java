package top.bootz.user.biz.persist.mysql;

import org.springframework.data.repository.CrudRepository;

import top.bootz.user.biz.entity.mysql.User;

public interface UserDao extends CrudRepository<User, Long> {

}
