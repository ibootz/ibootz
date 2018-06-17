package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;
import top.bootz.user.commons.converter.OperationAttributeConverter;
import top.bootz.user.commons.dictionary.OperationEnum;

/**
 * 功能操作表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_operation")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Operation extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 操作名称, 取自OperationType, 调用其name()方法得到的值 */
	private OperationEnum name;

	private DisableTypeEnum disable;

	@Convert(converter = OperationAttributeConverter.class)
	@Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' comment '操作名称'")
	public OperationEnum getName() {
		return name;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
