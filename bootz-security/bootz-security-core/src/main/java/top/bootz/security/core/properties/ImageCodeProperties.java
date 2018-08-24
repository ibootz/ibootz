/**
 * 
 */
package top.bootz.security.core.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图片验证码配置项
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午10:54:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 图片宽
     */
    private int width = 80;

    /**
     * 图片高
     */
    private int height = 32;

}
