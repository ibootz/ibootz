package top.bootz.core.base.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Desc: 为所有继承该Base实体的Mysql实体类提供唯一标示字段和审计字段
 *
 * @author John 2018年6月10日 下午6:05:12 <br/>
 */

@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseMysqlEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected Long creator; // 创建者

	protected LocalDateTime createTime; // 创建时间

	protected Long updater; // 更新者

	protected LocalDateTime updateTime; // 更新时间

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "top.bootz.core.idgenerator.CustomIdGenerator")
	@Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "bigint(64) default 0 comment '主键唯一标识'")
	public Long getId() {
		return id;
	}

	@CreatedBy
	@Column(name = "creator", nullable = false, columnDefinition = "bigint(64) default 0 comment '创建人ID'")
	public Long getCreator() {
		return creator;
	}

	@CreatedDate
	@Column(name = "create_time", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	@LastModifiedBy
	@Column(name = "updater", nullable = false, columnDefinition = "bigint(64) default 0 comment '更新人ID'")
	public Long getUpdater() {
		return updater;
	}

	@LastModifiedDate
	@Column(name = "update_time", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

}
