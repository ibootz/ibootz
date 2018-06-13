package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 页面元素表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_element")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Element extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 页面元素编码 */
	private String code;

	/** 父元素 */
	private Long parenId;

	/** 追踪路径 */
	private String path;

	/** 描述 */
	private String description;

	@Column(name = "code", nullable = false, columnDefinition = "varchar(32) default '' comment '页面元素编号'")
	public String getCode() {
		return code;
	}

	@Column(name = "paren_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '父元素ID'")
	public Long getParenId() {
		return parenId;
	}

	@Column(name = "path", nullable = false, columnDefinition = "varchar(256) default '' comment '追踪路径'")
	public String getPath() {
		return path;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(128) default '' comment '描述'")
	public String getDescription() {
		return description;
	}

}
