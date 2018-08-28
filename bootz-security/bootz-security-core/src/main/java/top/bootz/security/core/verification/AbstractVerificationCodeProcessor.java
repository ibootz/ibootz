package top.bootz.security.core.verification;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 抽象的图片验证码处理器
 * 
 * @param <C>
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:46:06
 */

public abstract class AbstractVerificationCodeProcessor<C extends VerificationCode>
        implements VerificationCodeProcessor {

    /**
     * 收集系统中所有的 {@link VerificationCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, VerificationCodeGenerator> verificationCodeGenerators;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C verificationCode = generate(request);
        save(request, verificationCode);
        send(request, verificationCode);
    }

    /**
     * 生成校验码
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getVerificationCodeType().toString().toLowerCase();
        String generatorName = type + VerificationCodeProcessorHolder.VERIFICATION_CODE_GENERATOR_SUFFIX;
        VerificationCodeGenerator verificationCodeGenerator = verificationCodeGenerators.get(generatorName);
        if (verificationCodeGenerator == null) {
            throw new VerificationCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) verificationCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     * 
     * @param request
     * @param verificationCode
     */
    private void save(ServletWebRequest request, C verificationCode) {
        VerificationCode code = new VerificationCode(verificationCode.getCode(), verificationCode.getExpireTime());
        verificationCodeRepository.save(request, code, getVerificationCodeType());
    }

    /**
     * 发送校验码，由子类实现
     * 
     * @param request
     * @param verificationCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C verificationCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     * 
     * @return
     */
    private VerificationCodeType getVerificationCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(),
                VerificationCodeProcessorHolder.VERIFICATION_CODE_PROCESSOR_SUFFIX);
        return VerificationCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        VerificationCodeType codeType = getVerificationCodeType();

        C codeInSession = (C) verificationCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new VerificationCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new VerificationCodeException(codeType.getDisplayName() + "的值不能为空");
        }

        if (codeInSession == null) {
            throw new VerificationCodeException(codeType.getDisplayName() + "不存在");
        }

        if (codeInSession.isExpried()) {
            verificationCodeRepository.remove(request, codeType);
            throw new VerificationCodeException(codeType.getDisplayName() + "已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new VerificationCodeException(codeType.getDisplayName() + "不匹配");
        }

        verificationCodeRepository.remove(request, codeType);

    }

}
