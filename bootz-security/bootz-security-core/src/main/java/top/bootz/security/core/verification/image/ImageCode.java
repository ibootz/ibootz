package top.bootz.security.core.verification.image;

import java.awt.image.BufferedImage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.security.core.verification.VerificationCode;

/**
 * 图形验证码
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月21日 下午10:54:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageCode extends VerificationCode {

    private static final long serialVersionUID = 1L;

    private transient BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

}
