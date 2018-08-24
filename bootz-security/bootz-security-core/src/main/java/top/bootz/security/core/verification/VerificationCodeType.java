package top.bootz.security.core.verification;

import top.bootz.security.core.SecurityConstants;

/**
 * 校验码类型
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月24日 下午8:22:57
 */
public enum VerificationCodeType {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中获取的参数的名字
     * 
     * @return
     */
    public abstract String getParamNameOnValidate();

}
