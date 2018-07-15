package top.bootz.user.entity.redis;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 
 * @author John
 * @time 2018年6月19日 下午8:42:48
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "{PingCache}", timeToLive = 60) // {}大括号包裹着keyPrefix可以保证在redis集群模式下，该实体的数据都存储在同一个slot中
public class PingCache extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime now = LocalDateTime.now();

}
