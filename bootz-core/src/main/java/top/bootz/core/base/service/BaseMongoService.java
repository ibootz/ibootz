package top.bootz.core.base.service;

import com.mongodb.client.result.UpdateResult;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.service.mongo
 * @Description : TODO
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-29 下午11:26
 */

@NoArgsConstructor
public class BaseMongoService<T> {

    private MongoTemplate mongoTemplate;

    private Class<T> targetType;

    public BaseMongoService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        ParameterizedType pType = (ParameterizedType) this.getClass().getGenericSuperclass();
        targetType = (Class<T>) pType.getActualTypeArguments()[0];
    }

    /**
     * 单独更新指定的某几个字段
     */
    public UpdateResult updateFiled(ObjectId id, Map<String, Object> fieldValueMap) {
        Update update = new Update();
        for (Map.Entry<String, Object> entry : fieldValueMap.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return this.mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), update, targetType);
    }

}
