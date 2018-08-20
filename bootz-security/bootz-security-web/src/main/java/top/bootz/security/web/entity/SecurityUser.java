package top.bootz.security.web.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class SecurityUser implements UserDetails, CredentialsContainer {

	private static final long serialVersionUID = -8241109321740519037L;

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
		if (this.user != null) {
			return this.user.getPassword();
		}
		return null;
	}

	@Override
	public String getUsername() {
		if (this.user != null) {
			return this.user.getUsername();
		}
		return null;
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

}
