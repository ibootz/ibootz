package top.bootz.security.core.verification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.bootz.core.base.entity.BaseEntity;

/**
 * 验证码信息封装类
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月22日 上午1:18:48
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VerificationCode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String code;

    private LocalDateTime expireTime;

    public VerificationCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

}
