package top.bootz.core.base.service;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasBuilder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月2日 下午9:11:19
 */

@Slf4j
@NoArgsConstructor
public class BaseElasticService {

    private ElasticsearchTemplate elasticsearchTemplate;

    public BaseElasticService(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * 为索引添加添加别名
     * 
     * @Author : Zhangq <momogoing@163.com>
     * @CreationDate : 2018年7月2日 下午10:26:51
     */
    public void addAlias(String indexName, String aliasName) {
        AliasBuilder aliasBuider = new AliasBuilder();
        aliasBuider.withIndexName(indexName);
        aliasBuider.withAliasName(aliasName);
        aliasBuider.build();
        boolean reuslt = elasticsearchTemplate.addAlias(aliasBuider.build());
        log.info("Add alias result:{}, indexName:{}, aliasName:{}", reuslt, indexName, aliasName);
    }

}
