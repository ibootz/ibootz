package top.bootz.user.repository.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.bootz.user.entity.mysql.auth.MapAuthorityMenu;

/**
 * @author John
 * 2018年6月11日 下午10:01:02
 */

public interface MapAuthorityMenuDao extends JpaRepository<MapAuthorityMenu, Long>, JpaSpecificationExecutor<MapAuthorityMenu> {

}
