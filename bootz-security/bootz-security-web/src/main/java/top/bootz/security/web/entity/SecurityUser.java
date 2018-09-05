package top.bootz.security.web.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails, SocialUserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;

    private User user;

    private List<GrantedAuthority> authorities;

    public SecurityUser(User user, List<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public SecurityUser(User user) {
        this.user = user;
    }

    public SecurityUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.getCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        if (user != null)
            user.setPassword(null);
    }

    @Override
    public String getUserId() {
        return String.valueOf(this.user.getId());
    }

}
