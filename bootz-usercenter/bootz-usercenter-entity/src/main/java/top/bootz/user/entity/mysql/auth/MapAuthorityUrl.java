package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

@Entity
@Table(name = "uc_map_authority_url")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapAuthorityUrl extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long authorityId;

	private Long urlId;

	@Column(name = "authority_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-权限ID'")
	public Long getAuthorityId() {
		return authorityId;
	}

	@Column(name = "url_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-可访问url前缀ID'")
	public Long getUrlId() {
		return urlId;
	}

}
