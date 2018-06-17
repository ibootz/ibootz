package top.bootz.usercenter.controller.ping;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import top.bootz.core.base.dto.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.usercenter.controller.BaseController;
import top.bootz.usercenter.view.pong.Pong;

@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

	/**
	 * 相应调用发的测试请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RestMessage> ping(HttpServletRequest request) {
		// 1. 测试Jpa相关配置和Mysql服务器
		boolean mysql = false;

		// 2. 测试Redis相关配置和Redis服务器
		boolean redis = false;

		// 3. 测试Mongodb相关配置和Mongo服务器
		boolean mongodb = false;

		// 4. 测试Elastic相关配置和Elastic服务器
		boolean elastic = false;

		// 5. 测试Rabbitmq相关配置和rabbitmq服务器
		boolean rabbitmq = false;

		Pong pong = new Pong(mysql, redis, mongodb, elastic, rabbitmq);
		return buildRestMessage(HttpStatus.OK, MessageStatusEnum.SUCCESS, pong, null);
	}

}
