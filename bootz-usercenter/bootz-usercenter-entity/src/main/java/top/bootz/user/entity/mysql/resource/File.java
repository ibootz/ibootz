package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.core.converter.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * 文件表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_file", indexes = { @Index(columnList = "name", name = "idx_uc_file_name", unique = true) })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 文件名 */
	private String name;

	/** 文件路径 */
	private String path;

	/** 文件描述 */
	private String description;

	/** 是否处于不可用状态 */
	private DisableTypeEnum disable;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' comment '文件名'")
	public String getName() {
		return name;
	}

	@Column(name = "path", nullable = false, columnDefinition = "varchar(255) default '' comment '文件路径'")
	public String getPath() {
		return path;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(255) default '' comment '文件描述'")
	public String getDescription() {
		return description;
	}

	@Convert(converter = DisableTypeAttributeConverter.class)
	@Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
	public DisableTypeEnum getDisable() {
		return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
	}

}
