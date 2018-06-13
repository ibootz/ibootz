package top.bootz.user.properties;

import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.bootz.commons.generator.IdGenerator;

/**
 * 配置主键生成器
 * 
 * @author John
 * @dateTime: 2018年6月10日 下午10:47:07
 */

@Data
@NoArgsConstructor
@Validated
@ConfigurationProperties(prefix = "id-generator")
public class IdGeneratorProperties {

	@Range(min = 0, max = IdGenerator.MAX_WORKER_ID)
	private Long workId;

	@Range(min = 0, max = IdGenerator.MAX_DATACENTER_ID)
	private Long dataCenterId;

}
