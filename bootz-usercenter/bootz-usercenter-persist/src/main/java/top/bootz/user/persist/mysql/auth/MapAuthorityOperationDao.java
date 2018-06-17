package top.bootz.user.persist.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.auth.MapAuthorityOperation;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface MapAuthorityOperationDao extends JpaRepository<MapAuthorityOperation, Long> {

}
