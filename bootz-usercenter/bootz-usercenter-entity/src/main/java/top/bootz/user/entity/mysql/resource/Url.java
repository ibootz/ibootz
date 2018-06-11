package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * Spring Security 中该用户可以访问的url前缀，相当于一种资源的控制
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_url")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 请求路径前缀 */
	private String urlExpression;

	@Column(name = "url_expression", nullable = false, columnDefinition = "varchar(32) default '' comment '请求路径前缀'")
	public String getUrlExpression() {
		return urlExpression;
	}

}
