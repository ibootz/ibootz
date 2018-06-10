package top.bootz.user.persist.mysql.user;

import org.springframework.data.repository.CrudRepository;

import top.bootz.user.dao.entity.mysql.user.User;

public interface UserDao extends CrudRepository<User, Long>, UserCustomDao {

}
