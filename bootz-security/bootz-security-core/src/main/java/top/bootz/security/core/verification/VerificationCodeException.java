package top.bootz.security.core.verification;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码验证失败异常
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月22日 上午1:22:50
 */
public class VerificationCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public VerificationCodeException(String msg) {
		super(msg);
	}

}
