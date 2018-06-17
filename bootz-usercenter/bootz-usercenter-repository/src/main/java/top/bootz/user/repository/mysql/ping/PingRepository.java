package top.bootz.user.repository.mysql.ping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.ping.Ping;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface PingRepository
		extends JpaRepository<Ping, Long>, JpaSpecificationExecutor<Ping>, QuerydslPredicateExecutor<Ping> {

}
