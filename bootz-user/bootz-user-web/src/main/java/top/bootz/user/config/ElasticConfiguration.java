package top.bootz.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月2日 上午12:25:27
 */

@Configuration
@EnableElasticsearchRepositories(basePackages = {"top.bootz.user.repository.elastic"})
public class ElasticConfiguration {

    @Bean
    @Primary
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ObjectMapper objectMapper) {
        return new ElasticsearchTemplate(client, new CustomEntityMapper(objectMapper));
    }

    public static class CustomEntityMapper implements EntityMapper {

        private ObjectMapper objectMapper;

        CustomEntityMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public String mapToString(Object object) throws IOException {
            return objectMapper.writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
            return objectMapper.readValue(source, clazz);
        }
    }

}
