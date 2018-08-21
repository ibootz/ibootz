package top.bootz.security.core.properties;

/**
 * 验证码配置
 * 
 * @author zhailiang
 *
 */
public class ValidateCodeProperties {

	/**
	 * 图片验证码配置
	 */
	private CaptchaCodeProperties image = new CaptchaCodeProperties();
	/**
	 * 短信验证码配置
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();

	public CaptchaCodeProperties getImage() {
		return image;
	}

	public void setImage(CaptchaCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}

}
