package top.bootz.user.entity.elastic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import lombok.Data;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月30日 下午7:38:57
 */

@Data
@Mapping(mappingPath = "mapping/ping_mapping.json") // 通过外部配置文件的方式来配置mapping，避免通过@Field字段来配置，不支持ik中文分词器的问题
@Document(indexName = PingElastic.INDEX, type = PingElastic.TYPE, shards = 3, replicas = 1, refreshInterval = "10s")
public class PingElastic implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String INDEX = "ping-v1";

	public static final String TYPE = "ping";

	public static final String ALIAS = "ping";

	@Id
	private Long id;

	private Integer count = 0; // 订单数量

	private String buyerName = ""; // 卖家姓名

	private Double price = 0d; // 订单价值

	private Set<PingItem> pingItems = new HashSet<>(); // 订单条目

	private LocalDateTime createTime; // 订单创建时间

}
