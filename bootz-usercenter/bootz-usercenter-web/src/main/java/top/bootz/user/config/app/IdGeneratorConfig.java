package top.bootz.user.config.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.bootz.commons.generator.IdGenerator;
import top.bootz.user.properties.IdGeneratorProperties;

/**
 * 
 * @author John
 * @dateTime: 2018年6月13日 下午11:36:00
 */

@Configuration
public class IdGeneratorConfig {

	@Autowired
	private IdGeneratorProperties idGeneratorProperties;

	@Bean
	@ConditionalOnMissingBean
	public IdGenerator idGenerator() {
		IdGenerator idGenerator = new IdGenerator();
		idGenerator.setWorkerId(idGeneratorProperties.getWorkId());
		idGenerator.setDataCenterId(idGeneratorProperties.getDataCenterId());
		return idGenerator;
	}

}
