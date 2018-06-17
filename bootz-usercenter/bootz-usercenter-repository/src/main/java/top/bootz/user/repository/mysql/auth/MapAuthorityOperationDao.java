package top.bootz.user.repository.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.bootz.user.entity.mysql.auth.MapAuthorityOperation;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface MapAuthorityOperationDao extends JpaRepository<MapAuthorityOperation, Long>, JpaSpecificationExecutor<MapAuthorityOperation> {

}
