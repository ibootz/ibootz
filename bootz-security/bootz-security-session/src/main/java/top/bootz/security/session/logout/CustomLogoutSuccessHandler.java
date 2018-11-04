package top.bootz.security.session.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.bootz.core.base.message.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;

/**
 * 默认的退出成功处理器，如果设置了imooc.security.browser.signOutUrl，则跳到配置的地址上， 如果没配置，则返回json格式的响应。
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年11月4日 上午10:54:16
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    public CustomLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    private String signOutSuccessUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(new RestMessage<>(MessageStatusEnum.SUCCESS, "退出成功", null)));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }

    }

}
