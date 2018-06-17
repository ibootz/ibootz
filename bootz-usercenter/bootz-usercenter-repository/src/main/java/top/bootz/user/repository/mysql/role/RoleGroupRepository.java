package top.bootz.user.repository.mysql.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.role.RoleGroup;

/**
 * @author John 2018年6月11日 下午9:50:44
 */

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long>, JpaSpecificationExecutor<RoleGroup>,
		QuerydslPredicateExecutor<RoleGroup> {

}
