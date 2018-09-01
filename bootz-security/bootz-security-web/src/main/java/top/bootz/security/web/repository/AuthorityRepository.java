package top.bootz.security.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import top.bootz.security.web.entity.Authority;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午10:53:55
 */

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String authName);

}
