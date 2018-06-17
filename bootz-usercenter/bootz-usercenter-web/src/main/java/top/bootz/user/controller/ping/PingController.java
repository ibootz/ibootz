package top.bootz.user.controller.ping;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.entity.BaseMessage;
import top.bootz.user.controller.BaseController;
import top.bootz.user.view.pong.Pong;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

	@Autowired
	private MessageSource messageSource;

	/**
	 * 相应调用发的测试请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Pong ping(HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String message = messageSource.getMessage("ping.success.message", null, locale);
		BaseMessage baseMessage = JsonHelper.fromJSON(message, BaseMessage.class);
		Pong pong = new Pong("success", baseMessage.getMessage());
		log.debug("message [" + message + "] locale language [" + locale.getLanguage() + "]");
		return pong;
	}

}
