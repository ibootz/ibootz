package top.bootz.usercenter.controller.ping;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.bootz.core.base.dto.RestMessage;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.user.entity.mysql.ping.Ping;
import top.bootz.user.service.elastic.ElasticPingService;
import top.bootz.user.service.mongo.MongoPingService;
import top.bootz.user.service.mysql.PingService;
import top.bootz.user.service.rabbit.RabbitService;
import top.bootz.usercenter.controller.BaseController;
import top.bootz.usercenter.view.pong.Pong;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

	@Autowired
	private PingService pingService;

	@Autowired
	private MongoPingService mongoPingService;

	@Autowired
	private ElasticPingService elasticPingService;

	@Autowired
	private RabbitService rabbitPingService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void test(HttpServletRequest request) {
		System.out.println("pingService [" + pingService + "]");
	}

	/**
	 * 相应调用发的测试请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseEntity<RestMessage> ping(HttpServletRequest request) {
		// 1. 测试Jpa相关配置和Mysql服务器
		boolean mysql = testMysql();

		// 2. 测试Redis相关配置和Redis服务器
		boolean redis = testRedis();

		// 3. 测试Mongodb相关配置和Mongo服务器
		boolean mongodb = testMongodb();

		// 4. 测试Elastic相关配置和Elastic服务器
		boolean elastic = testElastic();

		// 5. 测试Rabbitmq相关配置和rabbitmq服务器
		boolean rabbitmq = testRabbitmq();

		Pong pong = new Pong(mysql, redis, mongodb, elastic, rabbitmq);
		return buildRestMessage(HttpStatus.OK, MessageStatusEnum.SUCCESS, pong, null);
	}

	private boolean testRabbitmq() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean testElastic() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean testMongodb() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean testRedis() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean testMysql() {
		try {
			Ping ping = new Ping();
			pingService.save(ping);
			System.out.println("1 > " + ping);
			if (ping.getId() == null) {
				return false;
			}

			long id = ping.getId();
			Optional<Ping> pingOpt = pingService.find(id);
			if (!pingOpt.isPresent()) {
				return false;
			}

			// 异步删除 问题 TODO
			pingService.delete(pingOpt.get());
			
			Thread.sleep(5000L);
			
			pingOpt = pingService.find(id);
			System.out.println("2 > " + pingOpt);
			return pingOpt.isPresent() ? false : true;
		} catch (Exception e) {
			log.error("test mysql error.", e);
			return false;
		}
	}

}
