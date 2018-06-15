package top.bootz.user.persist.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.NoRepositoryBean;
import top.bootz.user.entity.mysql.user.Role;

/**
 * @author John
 * 2018年6月11日 下午9:52:50
 */

@NoRepositoryBean
public interface RoleDao extends JpaRepository<Role, Long> {

}
