package top.bootz.user.entity.mongo;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import top.bootz.commons.exception.AdviceException;
import top.bootz.core.base.entity.BaseEntity;

/**
 * API访问日志表
 */

@Setter
@Getter
@Document
@CompoundIndexes({ @CompoundIndex(name = "idx_class_method", def = "{'className': 1, 'methodName': 1}") })
public class AccessLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;

	@Indexed
	private String visitor; // 请求者身份标识(即 username)

	@Indexed
	private String token; // 请求者token

	private String requestIp; // 访问者Ip

	@Indexed
	private String requestUrl; // 请求的完整路径

	@Indexed
	private String className; // 类名

	@Indexed
	private String methodName; // 方法名

	private Map<String, String> inputParamMap = new LinkedHashMap<>(); // 入参

	private boolean returned; // 方法是否有返回值

	private String response; // 返回值的序列化结果

	private long tookMillSeconds; // 方法运行总耗时（毫秒）

	private boolean successed; // 方法是否成功结束运行

	private String exceptionTime; // 异常发生的时间

	private String errMsg; // 异常信息

	@CreatedDate
	private LocalDateTime createTime; // 创建时间

	@Transient
	@JsonIgnore
	private AdviceException adviceException; // 异常对象

	public void putInputParam(String key, String value) {
		if (!inputParamMap.containsKey(key)) {
			inputParamMap.put(key, value);
		}
	}

}
