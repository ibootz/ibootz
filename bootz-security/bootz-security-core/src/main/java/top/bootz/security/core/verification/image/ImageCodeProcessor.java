package top.bootz.security.core.verification.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.verification.AbstractVerificationCodeProcessor;

/**
 * 图片验证码处理器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午9:58:45
 */
@Component
public class ImageCodeProcessor extends AbstractVerificationCodeProcessor<ImageCode> {

    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }

}
