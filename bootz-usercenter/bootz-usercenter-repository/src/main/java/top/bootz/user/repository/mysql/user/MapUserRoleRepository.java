package top.bootz.user.repository.mysql.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.user.MapUserRole;

/**
 * @author John 2018年6月11日 下午10:00:10
 */

public interface MapUserRoleRepository extends JpaRepository<MapUserRole, Long>,
		JpaSpecificationExecutor<MapUserRoleRepository>, QuerydslPredicateExecutor<MapUserRoleRepository>{

}
