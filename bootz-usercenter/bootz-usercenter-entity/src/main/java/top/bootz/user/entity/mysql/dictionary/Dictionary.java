package top.bootz.user.entity.mysql.dictionary;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DictionaryTypeAttributeConverter;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DictionaryTypeEnum;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * Desc: 通用字典表
 * 
 * @author John 2018年6月10日 下午7:02:28 <br/>
 */

@Entity
@Table(name = "uc_dictionary", indexes = {
		@Index(columnList = "name,type", name = "idx_uc_dictionary_name_type", unique = true) })
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 字典表类型 */
	private DictionaryTypeEnum type;

	/** 字典name */
	private String name;

	/** 字典value */
	private String value;

	/** 字典备注 */
	private String description;

	/** 是否处于不可用状态 */
	private DisableTypeEnum disable;

	@Convert(converter = DictionaryTypeAttributeConverter.class)
	@Column(name = "type", nullable = false, columnDefinition = "varchar(16) default '' comment '字典类型'")
	public DictionaryTypeEnum getType() {
		return this.type;
	}

	@Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' comment '字典名'")
	public String getName() {
		return name;
	}

	@Column(name = "value", nullable = false, columnDefinition = "varchar(128) default '' comment '字典值'")
	public String getValue() {
		return value;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(255) default '' comment '字典备注'")
	public String getDescription() {
		return description;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
