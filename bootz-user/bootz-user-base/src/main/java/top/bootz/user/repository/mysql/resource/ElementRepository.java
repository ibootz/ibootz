package top.bootz.user.repository.mysql.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.resource.Element;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface ElementRepository
        extends JpaRepository<Element, Long>, JpaSpecificationExecutor<Element>, QuerydslPredicateExecutor<Element> {

}
