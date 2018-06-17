package top.bootz.user.persist.mysql.resource;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.resource.File;

/**
 * @author John
 * 2018年6月11日 下午10:01:02
 */

public interface FileDao extends JpaRepository<File, Long> {

}
