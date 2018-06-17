package top.bootz.user.repository.mysql.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.bootz.user.entity.mysql.resource.Url;

/**
 * @author John 2018年6月11日 下午10:01:02
 */

public interface UrlDao extends JpaRepository<Url, Long>, JpaSpecificationExecutor<Url> {

}
