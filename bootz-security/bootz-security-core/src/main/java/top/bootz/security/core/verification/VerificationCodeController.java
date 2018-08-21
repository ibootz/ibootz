package top.bootz.security.core.verification;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import top.bootz.security.core.verification.captcha.Captcha;

/**
 * 处理验证码请求
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月21日 下午11:09:57
 */

@RestController("/code")
public class VerificationCodeController {

	private static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

	@GetMapping(value = "/captcha")
	public void createCode(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Captcha captcha = createCaptcha(req);
		req.getSession(false).setAttribute(SESSION_KEY, captcha);
		ImageIO.write(captcha.getImage(), "JPEG", res.getOutputStream());
	}

	private Captcha createCaptcha(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

}
