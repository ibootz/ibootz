package top.bootz.core.generator;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import top.bootz.commons.constant.PatternConstants;
import top.bootz.commons.helper.DateHelper;
import top.bootz.commons.helper.SpringHelper;
import top.bootz.commons.helper.StringHelper;

public class IdGenerator implements Configurable, IdentifierGenerator {

	private final static Logger LOGGER = LogManager.getLogger(IdGenerator.class);

	private String redisConnectionFactory;

	private int idLength;

	private String perfix;

	private String timestamp;

	private String idKey;

	private long getNextSeqFromRedis() {
		// 每天都从1开始获取新的SEQ
		RedisAtomicLong redisAtomicLong = new RedisAtomicLong(idKey,
				(RedisConnectionFactory) SpringHelper.getBean(redisConnectionFactory));
		long seqNum = redisAtomicLong.incrementAndGet();
		if (seqNum == 1) { // 当第一次取出索引的时候，设置过期时间
			redisAtomicLong.expire(1, TimeUnit.DAYS); // 1天之后过期
		}
		LOGGER.debug("Obtain seqNum from redis [" + seqNum + "]");
		return seqNum;
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
		long seqNum = getNextSeqFromRedis();
		int zeroLength = idLength - perfix.length() - timestamp.length() - String.valueOf(seqNum).length();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < zeroLength; i++) {
			buf.append("0");
		}
		String time = DateHelper.date2str(DateHelper.now(), PatternConstants.DATE_FORMAT_PATTERN_6);
		return this.perfix + time + buf + seqNum;
	}

	@Override
	public void configure(Type type, Properties params, ServiceRegistry arg2) throws MappingException {
		this.redisConnectionFactory = params.getProperty("redisConnectionFactory");
		this.idLength = Integer.parseInt(params.getProperty("idLength"));
		this.perfix = params.getProperty("perfix");
		this.timestamp = DateHelper.date2str(DateHelper.now(), PatternConstants.DATE_FORMAT_PATTERN_4);
		this.idKey = StringHelper.joinWith("_", perfix, timestamp);
	}

	// for testing
	// public static RedisClusterConfiguration redisClusterConfiguration() {
	// RedisClusterConfiguration clusterConfiguration = new
	// RedisClusterConfiguration(
	// Arrays.asList("192.168.199.105:6379", "192.168.199.105:6380",
	// "192.168.199.105:6381",
	// "192.168.199.105:6382", "192.168.199.105:6383", "192.168.199.105:6384"));
	// clusterConfiguration.setMaxRedirects(5);
	// return clusterConfiguration;
	// }
	//
	// // for testing
	// public static JedisConnectionFactory jedisConnectionFactory() {
	// JedisConnectionFactory jedisConnectionFactory = new
	// JedisConnectionFactory(redisClusterConfiguration(),
	// jedisPoolConfig());
	// jedisConnectionFactory.setUsePool(true);
	// jedisConnectionFactory.setTimeout(0);
	// jedisConnectionFactory.afterPropertiesSet();
	// return jedisConnectionFactory;
	// }
	//
	// // for testing
	// public static JedisPoolConfig jedisPoolConfig() {
	// JedisPoolConfig poolConfig = new JedisPoolConfig();
	// poolConfig.setMaxTotal(10);
	// poolConfig.setMaxIdle(5);
	// poolConfig.setMaxWaitMillis(30000);
	// poolConfig.setTestOnBorrow(true);
	// return poolConfig;
	// }
	//
	// // for testing
	// public static void main(String[] args) {
	// int idLength = 24;
	// String perfix = "OSID";
	// String timestamp = DateHelper.date2str(new Date(),
	// PatternConstants.DATE_FORMAT_PATTERN_4);
	// String idKey = perfix + "_" + timestamp;
	// for (int i = 0; i < 2000; i++) {
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// generate(idLength, perfix, timestamp, idKey);
	// }
	// }).start();
	// }
	// }
	//
	// protected synchronized static void generate(int idLength, String perfix,
	// String timestamp, String idKey) {
	// RedisAtomicLong redisAtomicLong = new RedisAtomicLong(idKey,
	// jedisConnectionFactory());
	// long seqNum = redisAtomicLong.incrementAndGet();
	// if (seqNum == 1) {
	// redisAtomicLong.expire(30, TimeUnit.DAYS);
	// }
	// int zeroLength = idLength - perfix.length() - timestamp.length()
	// - String.valueOf(seqNum).length();
	// String time = DateHelper.date2str(DateHelper.now(),
	// PatternConstants.DATE_FORMAT_PATTERN_6);
	// StringBuffer buf = new StringBuffer();
	// for (int i = 0; i < zeroLength; i++) {
	// buf.append("0");
	// }
	// String id = perfix + time + buf + seqNum;
	// System.out.println(id);
	// }

}
