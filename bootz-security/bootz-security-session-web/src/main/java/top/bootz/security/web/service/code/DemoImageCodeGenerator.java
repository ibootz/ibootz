package top.bootz.security.web.service.code;

import org.springframework.web.context.request.ServletWebRequest;

import lombok.extern.slf4j.Slf4j;
import top.bootz.security.core.verification.VerificationCodeGenerator;
import top.bootz.security.core.verification.image.ImageCode;

/**
 * 应用自定义图形验证码生成器，覆盖core中定义的默认的验证码生成逻辑
 * 
 * @author John
 * @datetime 2018年8月28日 下午11:30:42
 */

@Slf4j
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements VerificationCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        log.debug("更高级的图形验证码生成代码");
        return null;
    }

}
