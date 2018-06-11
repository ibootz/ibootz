package top.bootz.user.persist.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.user.Role;

/**
 * Desc: TODO
 * @author John
 * @dateTime: 2018年6月11日 下午9:52:50
 */

public interface RoleDao extends JpaRepository<Role, Long> {

}
