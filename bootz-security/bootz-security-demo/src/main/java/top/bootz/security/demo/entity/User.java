package top.bootz.security.demo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "demo_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	private String username;

	private String password;

	private boolean accountExpired;

	private boolean accountLocked;

	private boolean credentialsExpired;

	private Set<Role> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "int(11) default 0 comment '主键唯一标识'")
	public Long getId() {
		return id;
	}

	@Column(name = "username", nullable = false, columnDefinition = "varchar(64) default '' comment '用户名'")
	public String getUsername() {
		return this.username;
	}

	@Column(name = "password", nullable = false, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return this.password;
	}

	@Column(name = "account_expired", nullable = false, columnDefinition = "boolean default false comment '账户是否过期'")
	public boolean getAccountExpired() {
		return this.accountExpired;
	}

	@Column(name = "account_locked", nullable = false, columnDefinition = "boolean default false comment '账户是否锁定'")
	public boolean getAccountLocked() {
		return this.accountLocked;
	}

	@Column(name = "credentials_expired", nullable = false, columnDefinition = "boolean default false comment '凭证是否过期'")
	public boolean getCredentialsExpired() {
		return this.credentialsExpired;
	}

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") })
	public Set<Role> getRoles() {
		return roles;
	}

}
