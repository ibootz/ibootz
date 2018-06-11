package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 权限文件映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_authority_file")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapAuthorityFile extends BaseMysqlEntity {

	private static final long serialVersionUID = 1L;

	private Long authorityId;

	private Long fileId;

	@Column(name = "authority_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-权限ID'")
	public Long getAuthorityId() {
		return authorityId;
	}

	@Column(name = "file_id", nullable = false, columnDefinition = "bigint(64) default 0 comment '外键-文件ID'")
	public Long getFileId() {
		return fileId;
	}

}
