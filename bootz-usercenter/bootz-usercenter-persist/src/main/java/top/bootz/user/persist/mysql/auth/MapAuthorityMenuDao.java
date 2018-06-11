package top.bootz.user.persist.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.auth.MapAuthorityMenu;

/**
 * @author John
 * @dateTime: 2018年6月11日 下午10:01:02
 */

public interface MapAuthorityMenuDao extends JpaRepository<MapAuthorityMenu, Long> {

}
