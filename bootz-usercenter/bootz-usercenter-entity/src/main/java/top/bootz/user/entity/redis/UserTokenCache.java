package top.bootz.user.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 用户登录之后将用户token缓存到redis中，一个用户只会有一个token，多端共享同一token
 *
 * @author John
 * @time 2018年6月17日 下午4:23:15
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(timeToLive = 7200) // 缓存2小时
public class UserTokenCache extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    private Long userId;

    /**
     * 用户token
     */
    @Indexed
    private String token;

}
