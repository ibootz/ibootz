package top.bootz.core.base.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.converter.ActiveTypeAttributeConverter;
import top.bootz.core.converter.DictionaryTypeAttributeConverter;
import top.bootz.core.dictionary.ActiveTypeEnum;
import top.bootz.core.dictionary.DictionaryTypeEnum;

/**
 * Desc: 通用字典表
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午7:02:28 <br/>
 */

@Entity
@Table(name = "core_dictionary")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 字典表类型 */
	private DictionaryTypeEnum type;

	/** 字典key */
	private String key;

	/** 字典value */
	private String value;

	/** 字典备注 */
	private String desc;

	/** 字典是否启用 */
	private ActiveTypeEnum status;

	@Convert(converter = DictionaryTypeAttributeConverter.class)
	@Column(name = "type", nullable = false, columnDefinition = "varchar(16) default '' comment '字典类型'")
	public DictionaryTypeEnum getType() {
		return this.type;
	}

	@Column(name = "key", nullable = false, columnDefinition = "varchar(64) default '' comment '字典key'")
	public String getKey() {
		return key;
	}

	@Column(name = "value", nullable = false, columnDefinition = "varchar(128) default '' comment '字典值'")
	public String getValue() {
		return value;
	}

	@Column(name = "desc", nullable = false, columnDefinition = "varchar(255) default '' comment '字典备注'")
	public String getDesc() {
		return desc;
	}

	@Convert(converter = ActiveTypeAttributeConverter.class)
	@Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default '' comment '字典状态'")
	public ActiveTypeEnum getStatus() {
		return status;
	}

}
