package top.bootz.security.session.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.security.core.properties.LoginType;
import top.bootz.security.core.properties.SecurityProperties;

/**
 * 登录认证成功之后的回调处理器
 * 
 * @author Zhangq - momogoing@163.com
 * @datetime 2018年8月20日 下午8:27:45
 */
@Slf4j
@Component
public class SessionAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String authInfo = JsonHelper.toJSON(authentication);
		log.debug("authentication success! authentication:{}", authInfo);

		// 如果应用配置了采用json的方式来处理登录认证成功事件，那么这里返回json响应
		if (LoginType.JOIN.equals(securityProperties.getSession().getLoginType())) {
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(authInfo);
			clearAuthenticationAttributes(request);
		} else { // 否则调用父类的方法，采用redirect的方式
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
