package top.bootz.security.core.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * 默认的 UserDetailsService 实现 不做任何处理，只在控制台打印一句日志，然后抛出异常，提醒业务系统自己配置 UserDetailsService。
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年8月29日 上午12:16:16
 */
@Slf4j
public class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.warn("请配置 UserDetailsService 接口的实现.");
        throw new UsernameNotFoundException(username);
    }

}
