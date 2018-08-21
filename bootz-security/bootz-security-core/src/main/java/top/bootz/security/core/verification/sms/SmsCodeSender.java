package top.bootz.security.core.verification.sms;

/**
 * @author zhailiang
 *
 */
public interface SmsCodeSender {

	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
