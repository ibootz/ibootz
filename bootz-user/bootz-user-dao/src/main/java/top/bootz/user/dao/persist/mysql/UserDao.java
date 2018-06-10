package top.bootz.user.dao.persist.mysql;

import org.springframework.data.repository.CrudRepository;

import top.bootz.user.dao.entity.mysql.User;

public interface UserDao extends CrudRepository<User, Long> {

}
