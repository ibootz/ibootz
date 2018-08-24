package top.bootz.security.core.verification;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月22日 上午1:21:18
 */
public interface VerificationCodeGenerator {

    String VERIFICATION_CODE_GENERATOR_SUFFIX = "CodeGenerator";

    /**
     * 生成校验码
     * 
     * @param request
     * @return
     */
    VerificationCode generate(ServletWebRequest request);

}
