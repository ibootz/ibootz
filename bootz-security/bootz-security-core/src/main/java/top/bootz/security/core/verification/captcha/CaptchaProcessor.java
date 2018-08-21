package top.bootz.security.core.verification.captcha;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.verification.AbstractVerificationCodeProcessor;

/**
 * 图片验证码处理器
 * 
 * @author zhailiang
 *
 */
@Component
public class CaptchaProcessor extends AbstractVerificationCodeProcessor<Captcha> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, Captcha captcha) throws Exception {
		ImageIO.write(captcha.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
