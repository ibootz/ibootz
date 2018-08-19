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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "demo_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	private String name;

	// private Set<User> users;

	private Set<Authority> authorities;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "int(11) default 0 comment '主键唯一标识'")
	public Long getId() {
		return id;
	}

	@Column(name = "name", nullable = false, columnDefinition = "varchar(128) default '' comment '权限字符串'")
	public String getName() {
		return name;
	}

	// @ManyToMany(mappedBy = "roles")
	// public Set<User> getUsers() {
	// return users;
	// }

	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "auth_id", referencedColumnName = "id") })
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	@JsonBackReference // 避免json序列化是死循环
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	// @JsonBackReference
	// public void setUsers(Set<User> users) {
	// this.users = users;
	// }

}
