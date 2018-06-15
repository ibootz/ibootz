package top.bootz.user.persist.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.NoRepositoryBean;
import top.bootz.user.entity.mysql.user.RoleGroup;

/**
 * @author John
 * 2018年6月11日 下午9:50:44
 */

@NoRepositoryBean
public interface RoleGroupDao extends JpaRepository<RoleGroup, Long> {

}
