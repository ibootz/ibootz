package top.bootz.user.repository.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.auth.Authority;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority>,
		QuerydslPredicateExecutor<Authority> {

}
