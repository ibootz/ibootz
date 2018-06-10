package top.bootz.user.config.subconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = { "top.bootz.user.biz.persist.elastic" })
public class ElasticConfig {

}
