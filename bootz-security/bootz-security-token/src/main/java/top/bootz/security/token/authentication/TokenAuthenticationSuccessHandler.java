package top.bootz.security.token.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.session.LoginResponseType;

/**
 * 登录认证成功之后的回调处理器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月20日 下午8:27:45
 */
@Component
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected MessageSource messages;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 如果应用配置了采用json的方式来处理登录认证成功事件，那么这里返回json响应
        if (LoginResponseType.JSON.equals(securityProperties.getSession().getLoginType())) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            RestMessage<UserDetails> restMessage = new RestMessage<>(MessageStatusEnum.SUCCESS, userDetails, null);
            response.getWriter().write(JsonHelper.toJSON(restMessage));
            clearAuthenticationAttributes(request);
        } else { // 否则调用父类的方法，采用redirect的方式
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
