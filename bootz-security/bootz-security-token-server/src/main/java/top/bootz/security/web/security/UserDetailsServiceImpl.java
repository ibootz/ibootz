package top.bootz.security.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.SecurityUser;
import top.bootz.security.web.entity.User;
import top.bootz.security.web.service.UserService;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = this.userService.findByUserName(userName).orElseGet(
                () -> this.userService.findByMobile(userName).orElseThrow(() -> new UsernameNotFoundException(
                        String.format("No user found with username or mobile ['%s'].", userName))));
        return new SecurityUser(user);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        User user = this.userService.find(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException(String.format("No user found with userId: ['%s'].", userId)));
        return new SecurityUser(user);
    }

}
