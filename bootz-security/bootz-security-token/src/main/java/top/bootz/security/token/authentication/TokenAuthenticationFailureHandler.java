package top.bootz.security.token.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.constant.AppConstants;
import top.bootz.commons.constant.ExceptionConstants;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.message.ErrorMessage;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.session.LoginResponseType;

@Slf4j
@Component
public class TokenAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected MessageSource messages;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        // 如果应用配置了采用json的方式来处理登录认证失败事件，那么这里返回json响应
        if (LoginResponseType.JSON.equals(securityProperties.getSession().getLoginType())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            RestMessage<Null> restMessage = buildRestMessage(request, e);
            response.getWriter().write(JsonHelper.toJSON(restMessage));
        } else { // 否则调用父类的方法，采用redirect的方式
            super.onAuthenticationFailure(request, response, e);
        }

    }

    private RestMessage<Null> buildRestMessage(HttpServletRequest request, AuthenticationException e) {
        String message = messages.getMessage(ExceptionConstants.ErrorMessageKey.UNAUTHORIZED_EXCEPTION, null,
                e.getMessage(), AppConstants.AppLocale.getDefault(request));
        if (StringUtils.isBlank(message)) {
            log.warn("errorKey [" + ExceptionConstants.ErrorMessageKey.UNAUTHORIZED_EXCEPTION + "] 在消息资源文件中没有找到对应的消息体");
            message = messages.getMessage(ExceptionConstants.ErrorMessageKey.APPLICATION_EXCEPTION, null,
                    e.getMessage(), AppConstants.AppLocale.getDefault(request));
        }
        ErrorMessage error = buildErrorMessage(message, e);
        return new RestMessage<>(MessageStatusEnum.ERROR, null, error);
    }

    protected ErrorMessage buildErrorMessage(String message, Exception e) {
        ErrorMessage error = null;
        if (JsonHelper.isJsonStr(message)) {
            error = JsonHelper.fromJSON(message, ErrorMessage.class);
        } else {
            error = new ErrorMessage("0", message);
        }
        if ("null".equalsIgnoreCase(e.getMessage()) || StringUtils.isBlank(e.getMessage())) {
            error.setMoreInfo("空指针异常");
        } else {
            error.setMoreInfo(e.getMessage());
        }
        error.setThrowable(e);
        return error;
    }

}
