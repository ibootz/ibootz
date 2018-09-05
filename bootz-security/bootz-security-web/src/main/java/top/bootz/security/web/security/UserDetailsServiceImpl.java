package top.bootz.security.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.Role;
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
        User user = this.userService.findByUserName(userName);
        if (user == null) {
            user = this.userService.findByMobile(userName);
        }
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("No user found with username or mobile ['%s'].", userName));
        }
        List<GrantedAuthority> auth = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new SecurityUser(user, auth);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) {
        User user = this.userService.find(Long.valueOf(userId)).orElseThrow(
                () -> new RuntimeException(String.format("No user found with userId: ['%s'].", userId)));
        List<GrantedAuthority> auth = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new SecurityUser(user, auth);
    }

}
