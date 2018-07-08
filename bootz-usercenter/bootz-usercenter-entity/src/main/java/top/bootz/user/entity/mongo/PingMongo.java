package top.bootz.user.entity.mongo;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

/**
 * @Project : ibootz
 * @Package : top.bootz.user.entity.mongo
 * @Description : mongo可用性测试表
 * @Author : momogoing@163.com
 * @CreationDate : 2018-06-23 下午8:32
 */

@Document
@Setter
@Getter
@CompoundIndexes({ @CompoundIndex(name = "idx_ping_createtor_createTime", def = "{'createtor': 1, 'createTime': 1}") })
public class PingMongo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    @Indexed
    @CreatedBy
    private Long createtor;

    @Indexed
    @CreatedDate
    private LocalDateTime createTime;

}
