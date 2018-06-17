package top.bootz.user.repository.mysql.dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import top.bootz.user.entity.mysql.dictionary.Dictionary;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary>,
		QuerydslPredicateExecutor<Dictionary> {

}
