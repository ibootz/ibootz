package top.bootz.security.core.verification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import top.bootz.security.core.SecurityConstants;

/**
 * 处理验证码请求
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月21日 下午11:09:57
 */

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeProcessorHolder verificationCodeProcessorHolder;

    @GetMapping(SecurityConstants.DEFAULT_VERIFICATION_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        verificationCodeProcessorHolder.findVerificationCodeProcessor(type)
                .create(new ServletWebRequest(request, response));
    }

}
