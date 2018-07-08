package top.bootz.user.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import top.bootz.commons.snowflake.IdGenerator;
import top.bootz.user.config.properties.IdGeneratorProperties;

/**
 * 分布式全局唯一主键生成器配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:12:47
 */
@Configuration
public class IdGeneratorConfig {

    @Autowired
    private IdGeneratorProperties idGeneratorProperties;

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        IdGenerator idGenerator = new IdGenerator();
        idGenerator.setWorkerId(getWorkId());
        idGenerator.setDataCenterId(getDataCenterId());
        return idGenerator;
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
