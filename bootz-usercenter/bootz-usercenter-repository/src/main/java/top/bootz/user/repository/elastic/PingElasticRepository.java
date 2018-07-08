package top.bootz.user.repository.elastic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import top.bootz.user.entity.elastic.PingElastic;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月30日 下午11:13:51
 */

public interface PingElasticRepository extends ElasticsearchRepository<PingElastic, Long> {

    @Query("{\"bool\":{\"must\":{\"match\":{\"buyerName.raw\": \"?0\"}}}}")
    Optional<PingElastic> findByBuyerNameLike(String buyerNameLike);

    List<PingElastic> findByCreateTimeBetweenOrderByCreateTimeDesc(String startTime, String endTime);

}
