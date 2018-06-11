package top.bootz.user.persist.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.user.MapUserRole;

/**
 * @author John
 * @dateTime: 2018年6月11日 下午10:00:10
 */

public interface MapUserRoleDao extends JpaRepository<MapUserRole, Long> {

}
