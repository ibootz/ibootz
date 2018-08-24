package top.bootz.security.core.verification.sms;

public interface SmsCodeSender {

    /**
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);

}
