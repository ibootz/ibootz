package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.attribute.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 页面元素表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_element", indexes = { @Index(columnList = "code", name = "idx_uc_element_code", unique = true),
		@Index(columnList = "paren_id", name = "idx_uc_element_parenid") })
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "code" }, callSuper = false)
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

	/** 是否处于不可用状态 */
	private DisableTypeEnum disable;

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

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
