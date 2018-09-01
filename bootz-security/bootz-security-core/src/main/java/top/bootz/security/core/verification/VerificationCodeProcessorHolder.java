/**
 * 
 */
package top.bootz.security.core.verification;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校验码处理器管理器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:24:22
 */
@Component
public class VerificationCodeProcessorHolder {

    public static final String VERIFICATION_CODE_PROCESSOR_SUFFIX = "CodeProcessor";

    @Autowired
    private Map<String, VerificationCodeProcessor> verificationCodeProcessors;

    /**
     * @param type
     * @return
     */
    public VerificationCodeProcessor findVerificationCodeProcessor(VerificationCodeType type) {
        return findVerificationCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * @param type
     * @return
     */
    public VerificationCodeProcessor findVerificationCodeProcessor(String type) {
        String name = type.toLowerCase() + VerificationCodeProcessorHolder.VERIFICATION_CODE_PROCESSOR_SUFFIX;
        VerificationCodeProcessor processor = verificationCodeProcessors.get(name);
        if (processor == null) {
            throw new VerificationCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}
