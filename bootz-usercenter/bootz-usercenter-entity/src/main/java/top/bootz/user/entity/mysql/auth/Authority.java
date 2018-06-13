package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;
import top.bootz.user.commons.converter.AuthorityAttributeConverter;
import top.bootz.user.commons.dictionary.AuthorityEnum;

/**
 * 权限表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_authority")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 权限类型 */
	private AuthorityEnum type;

	/** 权限描述 */
	private String description;

	@Convert(converter = AuthorityAttributeConverter.class)
	@Column(name = "type", nullable = false, columnDefinition = "tinyint(1) default 0 comment '权限类型(menu, element, file, operation等)'")
	public AuthorityEnum getType() {
		return type;
	}

	@Column(name = "description", nullable = false, columnDefinition = "varchar(255) default '' comment '权限描述'")
	public String getDescription() {
		return description;
	}

}
