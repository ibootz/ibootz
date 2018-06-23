package top.bootz.usercenter.config.properties;

import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.bootz.commons.snowflake.IdGenerator;

/**
 * 配置主键生成器
 * 
 * @author John
 * 2018年6月10日 下午10:47:07
 */

@Data
@NoArgsConstructor
@Validated
@ConfigurationProperties(prefix = "custom.id-snowflake")
public class IdGeneratorProperties {

	@Range(min = 0, max = IdGenerator.MAX_WORKER_ID)
	private Long workerId;

	@Range(min = 0, max = IdGenerator.MAX_DATACENTER_ID)
	private Long dataCenterId;

}
