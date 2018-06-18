package top.bootz.user.entity.mysql.ping;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;
import top.bootz.core.base.entity.BaseMysqlEntity;

/**
 * 用来测试数据库状态的表
 *
 * @author John
 * @time 2018年6月18日 上午1:17:05
 */

@Entity
@Table(name = "uc_ping")
@Setter
public class Ping extends BaseMysqlEntity {

    private static final long serialVersionUID = 1L;

}
