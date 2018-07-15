package top.bootz.user.service.elastic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import top.bootz.core.base.service.BaseElasticService;
import top.bootz.user.entity.elastic.PingElastic;
import top.bootz.user.repository.elastic.PingElasticRepository;

/**
 * 
 * @author John
 * @time 2018年6月18日 下午2:43:37
 */

@Service
public class PingElasticService extends BaseElasticService {

    @Autowired
    private PingElasticRepository pingElasticRepository;

    @Autowired
    public PingElasticService(ElasticsearchTemplate elasticsearchTemplate) {
        super(elasticsearchTemplate);
    }

    public PingElastic save(PingElastic order) {
        return this.pingElasticRepository.save(order);
    }

    public Optional<PingElastic> findByBuyerNameLike(String buyerNameLike) {
        return this.pingElasticRepository.findByBuyerNameLike(buyerNameLike);
    }

    public List<PingElastic> findByCreateTimeBetweenOrderByCreateTimeDesc(LocalDateTime startTime,
            LocalDateTime endTime) {
        return this.pingElasticRepository.findByCreateTimeBetweenOrderByCreateTimeDesc(
                startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public void deleteOrders(List<PingElastic> pingOrders) {
        this.pingElasticRepository.deleteAll(pingOrders);
    }

}
