package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * Spring Security 中该用户可以访问的url前缀，相当于一种资源的控制
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_access_url")
public class AccessUrl extends BaseEntity {

	private static final long serialVersionUID = 1186408204756564325L;

	private String urlExpression;

	@Column(name = "url_expression", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '请求路径前缀'")
	public String getUrlExpression() {
		return urlExpression;
	}

	public void setUrlExpression(String urlExpression) {
		this.urlExpression = urlExpression;
	}

}
