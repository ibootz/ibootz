package top.bootz.core.base.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc: 为所有继承该Base实体的Mysql实体类提供唯一标示字段和审计字段
 *
 * @author John 2018年6月10日 下午6:05:12 <br/>
 */

@Setter
@Getter
public class BaseRedisEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	protected String creator; // 创建者

	protected LocalDateTime createTime; // 创建时间

	protected String updater; // 更新者

	protected LocalDateTime updateTime; // 更新时间

}
