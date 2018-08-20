package top.bootz.security.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.security.web.entity.Role;
import top.bootz.security.web.entity.SecurityUser;
import top.bootz.security.web.entity.User;
import top.bootz.security.web.service.UserService;


@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userService.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        }
        List<GrantedAuthority> auth = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new SecurityUser(user, auth);
    }

}
