package top.bootz.user.persist.mysql.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.user.entity.mysql.auth.MapAuthorityFile;

/**
 * @author John
 * @dateTime: 2018年6月11日 下午10:01:02
 */

public interface MapAuthorityFileDao extends JpaRepository<MapAuthorityFile, Long> {

}