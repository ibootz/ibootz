package top.bootz.user.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = { "top.bootz.user.persist.elastic" })
public class ElasticConfig {

}
