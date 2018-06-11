package top.bootz.user.persist.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.user.RoleGroup;

/**
 * @author John
 * @dateTime: 2018年6月11日 下午9:50:44
 */

public interface RoleGroupDao extends JpaRepository<RoleGroup, Long> {

}
