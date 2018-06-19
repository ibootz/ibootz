package top.bootz.usercenter.controller.ping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import top.bootz.user.entity.redis.PingCache;
import top.bootz.user.service.elastic.ElasticPingService;
import top.bootz.user.service.mongo.MongoPingService;
import top.bootz.user.service.mysql.PingService;
import top.bootz.user.service.rabbit.RabbitService;
import top.bootz.user.service.redis.PingCacheService;
import top.bootz.user.service.redis.RedisService;
import top.bootz.usercenter.controller.BaseController;
import top.bootz.usercenter.view.pong.Pong;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingController extends BaseController {

	@Autowired
	private PingService pingService;

	@Autowired
	private PingCacheService pingCacheService;

	@Autowired
	private RedisService redisService;

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
		String key = "";
 		String value = "";
		try {
			key = "ping:simple:key";
			value = "ping:simple:value";
			redisService.opsForValue(key, value);
		} catch (Exception e) {
			log.error("test redis opsForValue error.", e);
			return false;
		}

		try {
			key = "ping:hash:key";
			String hashKey1 = "ping:hash:hKey--1";
			String hashKey2 = "ping:hash:hKey--2";
			PingCache ping1 = new PingCache();
			PingCache ping2 = new PingCache();
			Map<String, PingCache> values = new HashMap<>();
			values.put(hashKey1, ping1);
			values.put(hashKey2, ping2);
			redisService.opsForHash(key, values);
		} catch (Exception e) {
			log.error("test redis opsForHash error.", e);
			return false;
		}

		try {
			key = "ping:list:key";
			List<PingCache> pingCaches = new ArrayList<>();
			PingCache ping1 = new PingCache();
			PingCache ping2 = new PingCache();
			pingCaches.add(ping1);
			pingCaches.add(ping2);
			redisService.opsForList(key, pingCaches);
		} catch (Exception e) {
			log.error("test redis opsForList error.", e);
			return false;
		}

		try {
			key = "ping:set:key";
			Set<PingCache> pingCaches = new HashSet<>();
			PingCache ping1 = new PingCache();
			PingCache ping2 = new PingCache();
			pingCaches.add(ping1);
			pingCaches.add(ping2);
			redisService.opsForSet(key, pingCaches);
		} catch (Exception e) {
			log.error("test redis opsForSet error.", e);
			return false;
		}

		try {
			PingCache pingCache = new PingCache();
			pingCacheService.save(pingCache);
			if (pingCache.getId() == null) {
				log.error("pingCache id is null. [{}]", pingCache);
				return false;
			}

			Optional<PingCache> opt = pingCacheService.find(pingCache.getId());
			if (!opt.isPresent()) {
				return false;
			}

			pingCacheService.delete(opt.get());

			opt = pingCacheService.find(pingCache.getId());
			return !opt.isPresent();
		} catch (Exception e) {
			log.error("test redis opsForSet error.", e);
			return false;
		}
	}

	private boolean testMysql() {
		try {
			Ping ping = new Ping();
			pingService.save(ping);
			if (ping.getId() == null) {
				log.error("ping id is null. [{}]", ping);
				return false;
			}
			Optional<Ping> pingOpt = pingService.find(ping.getId());
			if (!pingOpt.isPresent()) {
				log.error("ping is not found. [{}]");
				return false;
			}

			pingService.delete(pingOpt.get());

			pingOpt = pingService.find(ping.getId());
			return !pingOpt.isPresent();
		} catch (Exception e) {
			log.error("test mysql error.", e);
			return false;
		}
	}

}
