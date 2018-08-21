package top.bootz.security.session.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.security.core.properties.LoginType;
import top.bootz.security.core.properties.SecurityProperties;

@Slf4j
@Component
public class SessionAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.debug("authentication failure! error message:{}", exception.getMessage());

		// 如果应用配置了采用json的方式来处理登录认证失败事件，那么这里返回json响应
		if (LoginType.JOIN.equals(securityProperties.getSession().getLoginType())) {
			log.debug("Return JSON response");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.getWriter().write(JsonHelper.toJSON(exception));
		} else { // 否则调用父类的方法，采用redirect的方式
			super.onAuthenticationFailure(request, response, exception);
		}

	}

}
