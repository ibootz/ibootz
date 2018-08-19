package top.bootz.user.controller.ping;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.BooleanHelper;
import top.bootz.commons.helper.MapHelper;
import top.bootz.commons.helper.RandomHelper;
import top.bootz.commons.snowflake.IdGenerator;
import top.bootz.core.base.dto.BaseMessage;
import top.bootz.core.base.dto.RestMessage;
import top.bootz.core.dictionary.AppEnum;
import top.bootz.user.commons.constants.RabbitConstants.Exchange;
import top.bootz.user.commons.constants.RabbitConstants.RoutingKey;
import top.bootz.user.controller.UserBaseController;
import top.bootz.user.entity.elastic.PingElastic;
import top.bootz.user.entity.elastic.PingItem;
import top.bootz.user.entity.elastic.PingItemTag;
import top.bootz.user.entity.mongo.PingMongo;
import top.bootz.user.entity.mongo.RabbitMessageLog;
import top.bootz.user.entity.mysql.ping.Ping;
import top.bootz.user.entity.rabbit.PingMessage;
import top.bootz.user.entity.redis.PingCache;
import top.bootz.user.service.elastic.PingElasticService;
import top.bootz.user.service.mongo.PingMongoService;
import top.bootz.user.service.mongo.RabbitMessageLogService;
import top.bootz.user.service.mysql.PingService;
import top.bootz.user.service.rabbit.sender.PingMessageSender;
import top.bootz.user.service.redis.PingCacheService;
import top.bootz.user.service.redis.RedisService;
import top.bootz.user.view.pong.Pong;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingController extends UserBaseController {

	@Autowired
	private PingService pingService;

	@Autowired
	private PingCacheService pingCacheService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private PingMongoService pingMongoService;

	@Autowired
	private PingElasticService pingElasticService;

	@Autowired
	private PingMessageSender pingMessageSender;

	@Autowired
	private RabbitMessageLogService rabbitMessageLogService;

	@Autowired
	private IdGenerator idGenerator;

	/**
	 * 相应调用发的测试请求
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RestMessage ping(HttpServletRequest request) throws InterruptedException {
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
		return buildSuccessResponse(pong);
	}

	private boolean testRabbitmq() throws InterruptedException {
		String payload = "Just a ping!!!";
		BaseMessage message = new PingMessage(AppEnum.USER.getName(), new String[] { AppEnum.ORDER.getName() }, -1L,
				LocalDateTime.now(), payload);
		Long messageId = pingMessageSender.send(Exchange.DIRECT, RoutingKey.UC_DIRECT_PING, message,
				MapHelper.emptyMap());
		Thread.sleep(2000);
		Optional<RabbitMessageLog> opt = rabbitMessageLogService.findByMessageId(messageId);
		if (opt.isPresent()) {
			log.debug(opt.get().toString());
			return BooleanHelper.toSafeBoolean(opt.get().getReceived());
		}
		return false;
	}

	private boolean testElastic() {
		LocalDateTime startTime = LocalDateTime.now();
		int mockDataCount = 10;
		try {
			// 制造伪数据
			randomData(mockDataCount);
		} catch (Exception e) {
			log.error("mock elastic data error.", e);
			return false;
		}

		List<PingElastic> pingOrders;
		try {
			// 模拟查询
			pingOrders = pingElasticService.findByCreateTimeBetweenOrderByCreateTimeDesc(startTime,
					LocalDateTime.now());
			if (pingOrders == null || pingOrders.size() != mockDataCount) {
				return false;
			}
		} catch (Exception e) {
			log.error("find elastic data error.", e);
			return false;
		}

		try {
			// 删除数据
			pingElasticService.deleteOrders(pingOrders);
		} catch (Exception e) {
			log.error("delete elastic data error.", e);
			return false;
		}

		return true;
	}

	private void randomData(int mockDataCount) {
		for (int i = 0; i < mockDataCount; i++) {
			PingElastic ping = new PingElastic();
			ping.setId(idGenerator.nextId());
			ping.setBuyerName(RandomHelper.randomChineseName());
			ping.setCount(RandomHelper.randomInt(1, 99));
			ping.setCreateTime(LocalDateTime.now());
			ping.setPrice(RandomHelper.randomDouble(0.50, 99.99));
			Set<PingItem> pingItems = new HashSet<>();
			ping.setPingItems(pingItems);
			for (int j = 0; j < RandomHelper.randomInt(1, 9); j++) {
				PingItem pingItem = new PingItem();
				pingItems.add(pingItem);
				pingItem.setId(idGenerator.nextId());
				PingItemTag tag = new PingItemTag();
				pingItem.setPingItemTag(tag);
				tag.setDescription(RandomHelper.randomString(10, 999, RandomHelper.randomChinese(10, 300)));
				tag.setId(idGenerator.nextId());
				tag.setSku(RandomHelper.randomString(9));
			}
			pingElasticService.save(ping);
		}
	}

	private boolean testMongodb() {
		try {
			PingMongo ping = new PingMongo();
			pingMongoService.save(ping);
			if (ping.getId() == null) {
				log.error("pingMongo id is null. {}", ping);
				return false;
			}

			log.debug("mongo objectId: {}", ping.getId());

			Optional<PingMongo> pingOpt = pingMongoService.find(ping.getId());
			if (!pingOpt.isPresent()) {
				log.error("pingMongo is not found. {}");
				return false;
			}

			pingMongoService.delete(pingOpt.get());

			pingOpt = pingMongoService.find(ping.getId());
			return !pingOpt.isPresent();

		} catch (Exception e) {
			log.error("test mongodb error.", e);
		}

		return false;
	}

	private boolean testRedis() {
		String key;
		String value;
		try {
			key = "ping:simple:key";
			value = "ping:simple:value";
			redisService.opsForValue(key, value, 600);
		} catch (Exception e) {
			log.error("test redis opsForValue error.", e);
			return false;
		}

		try {
			key = "ping:hash:key";
			String hashKey1 = "hKey1";
			String hashKey2 = "hKey2";
			PingCache ping1 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			PingCache ping2 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			Map<String, PingCache> values = new HashMap<>();
			values.put(hashKey1, ping1);
			values.put(hashKey2, ping2);
			redisService.opsForHash(key, values, 600);
		} catch (Exception e) {
			log.error("test redis opsForHash error.", e);
			return false;
		}

		try {
			key = "ping:list:key";
			List<PingCache> pingCaches = new ArrayList<>();
			PingCache ping1 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			PingCache ping2 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			pingCaches.add(ping1);
			pingCaches.add(ping2);
			redisService.opsForList(key, pingCaches, 600);
		} catch (Exception e) {
			log.error("test redis opsForList error.", e);
			return false;
		}

		try {
			key = "ping:set:key";
			Set<PingCache> pingCaches = new HashSet<>();
			PingCache ping1 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			PingCache ping2 = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			pingCaches.add(ping1);
			pingCaches.add(ping2);
			redisService.opsForSet(key, pingCaches, 600);
		} catch (Exception e) {
			log.error("test redis opsForSet error.", e);
			return false;
		}

		try {
			PingCache pingCache = new PingCache("" + idGenerator.nextId(), LocalDateTime.now());
			pingCacheService.save(pingCache);
			if (pingCache.getId() == null) {
				log.error("pingCache id is null. {}", pingCache);
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
				log.error("ping id is null. {}", ping);
				return false;
			}
			System.out.println("1");
			Optional<Ping> pingOpt = pingService.find(ping.getId());
			if (!pingOpt.isPresent()) {
				log.error("ping is not found. {}");
				return false;
			}

			pingService.delete(pingOpt.get());

			System.out.println("2");
			pingOpt = pingService.find(ping.getId());
			return !pingOpt.isPresent();
		} catch (Exception e) {
			log.error("test mysql error.", e);
		}
		return false;
	}

}
