package top.bootz.user.dao.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 文件表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_file")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	/** 文件名 */
	private String name;

	/** 文件路径 */
	private String path;

	/** 文件描述 */
	private String desc;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' comment '文件名'")
	public String getName() {
		return name;
	}

	@Column(name = "path", nullable = false, columnDefinition = "varchar(255) default '' comment '文件路径'")
	public String getPath() {
		return path;
	}

	@Column(name = "desc", nullable = false, columnDefinition = "varchar(255) default '' comment '文件描述'")
	public String getDesc() {
		return desc;
	}

}
