package top.bootz.user.dao.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import top.bootz.core.base.entity.BaseEntity;

/**
 * 页面元素表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_element")
public class Element extends BaseEntity {

	private static final long serialVersionUID = 1099247390226277674L;

	private String code; // 页面元素编码

	private String parenId; // 父元素

	private String description; // 描述

	@Column(name = "code", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '页面元素编号'")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "paren_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '父元素ID'")
	public String getParenId() {
		return parenId;
	}

	public void setParenId(String parenId) {
		this.parenId = parenId;
	}

	@Column(name = "description", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '描述'")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
