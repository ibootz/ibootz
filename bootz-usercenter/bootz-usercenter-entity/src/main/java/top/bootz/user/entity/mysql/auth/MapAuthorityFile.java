package top.bootz.user.entity.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 关联表：权限-文件
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_map_authority_file", indexes = {
		@Index(columnList = "authority_id,file_id", name = "idx_uc_maf_authorityid_fileid", unique = true),
		@Index(columnList = "file_id", name = "idx_uc_maf_fileid") })
@Setter
@Getter
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
