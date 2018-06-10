package top.bootz.user.persist.mysql;

import org.springframework.data.repository.CrudRepository;

import top.bootz.user.dao.entity.mysql.User;

public interface UserDao extends CrudRepository<User, Long> {

}
