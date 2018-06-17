package top.bootz.usercenter.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.bootz.commons.snowflake.Snowflake;
import top.bootz.usercenter.config.properties.IdGeneratorProperties;

/**
 * @author John 2018年6月13日 下午11:36:00
 */

@Configuration
public class IdGeneratorConfig {

	@Autowired
	private IdGeneratorProperties idGeneratorProperties;

	@Bean
	@ConditionalOnMissingBean
	public Snowflake idGenerator() {
		Snowflake snowflake = new Snowflake();
		snowflake.setWorkerId(getWorkId());
		snowflake.setDataCenterId(getDataCenterId());
		return snowflake;
	}

	private long getWorkId() {
		String workerId = System.getProperty("bootz.id.snowflake.worker.id");
		if (StringUtils.isNotBlank(workerId)) {
			return Long.valueOf(workerId);

		}
		workerId = System.getenv("BOOTZ_ID_GENERATOR_WORKER_ID");
		if (StringUtils.isNotBlank(workerId)) {
			return Long.valueOf(workerId);
		}
		if (idGeneratorProperties.getWorkerId() != null) {
			return idGeneratorProperties.getWorkerId();
		}
		throw new IllegalArgumentException("workerId must not be null or blank!");
	}

	private long getDataCenterId() {
		String dataCenterId = System.getProperty("bootz.id.snowflake.datacenter.id");
		if (StringUtils.isNotBlank(dataCenterId)) {
			return Long.valueOf(dataCenterId);
		}
		dataCenterId = System.getenv("BOOTZ_ID_GENERATOR_DATACENTER_ID");
		if (StringUtils.isNotBlank(dataCenterId)) {
			return Long.valueOf(dataCenterId);
		}
		if (idGeneratorProperties.getDataCenterId() != null) {
			return idGeneratorProperties.getDataCenterId();
		}
		throw new IllegalArgumentException("dataCenter Id must not be null or blank!");
	}

}
