package top.bootz.user.persist.mysql.user;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long>, UserCustomDao {

}
