package top.bootz.user.config.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import top.bootz.commons.converter.DateToLocalDateConverter;
import top.bootz.commons.converter.DateToLocalDateTimeConverter;
import top.bootz.commons.converter.DateToTimestampConverter;
import top.bootz.commons.converter.LocalDateTimeToDateConverter;
import top.bootz.commons.converter.LocalDateToDateConverter;
import top.bootz.user.properties.MongoConfigProperties;

@Configuration
@EnableMongoRepositories(basePackages = { "top.bootz.user.biz.persist.mongo" })
@EnableMongoAuditing(auditorAwareRef = "mongoAuditorProvider", dateTimeProviderRef = "mongoDateTimeProvider", modifyOnCreate = false)
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private MongoConfigProperties configProperties;

	// 覆盖默认的MongoDbFactory
	@Bean
	@Override
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(mongoClient(), configProperties.getDatabase());
	}

	@Override
	protected String getDatabaseName() {
		return configProperties.getDbname();
	}

	@Bean
	@Override
	public MongoClient mongoClient() {
		// 客户端配置（连接数、副本集群验证）
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(configProperties.getConnectionsPerHost());
		builder.minConnectionsPerHost(configProperties.getMinConnectionsPerHost());
		if (configProperties.getReplicaSet() != null) {
			builder.requiredReplicaSetName(configProperties.getReplicaSet());
		}
		MongoClientOptions mongoClientOptions = builder.build();

		// MongoDB地址列表
		List<ServerAddress> serverAddresses = new ArrayList<>();
		for (String host : configProperties.getHosts()) {
			Integer index = configProperties.getHosts().indexOf(host);
			Integer port = configProperties.getPorts().get(index);

			ServerAddress serverAddress = new ServerAddress(host, port);
			serverAddresses.add(serverAddress);
		}

		// 连接认证
		MongoCredential mongoCredential = null;
		if (configProperties.getUsername() != null) {
			mongoCredential = MongoCredential.createScramSha1Credential(
					configProperties.getUsername(), configProperties.getAuthenticationDatabase() != null
							? configProperties.getAuthenticationDatabase() : configProperties.getDatabase(),
					configProperties.getPassword().toCharArray());
		}

		// 创建客户端和Factory
		MongoClient mongoClient = null;
		if (mongoCredential != null) {
			mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
		} else {
			mongoClient = new MongoClient(serverAddresses);
		}
		return mongoClient;
	}

	/**
	 * 自定义转换器，处理date到timestamp的转换问题
	 */
	@Override
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(DateToTimestampConverter.INSTANCE);
		converters.add(DateToLocalDateConverter.INSTANCE);
		converters.add(DateToLocalDateTimeConverter.INSTANCE);
		converters.add(LocalDateTimeToDateConverter.INSTANCE);
		converters.add(LocalDateToDateConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}

	/**
	 * 添加操作时间审计
	 * 
	 * @return
	 */
	@Bean
	public DateTimeProvider mongoDateTimeProvider() {
		return CurrentDateTimeProvider.INSTANCE;
	}

	/**
	 * 添加操作人审计 TODO 暂时没有实现登录授权功能,所以这里暂时没法获取当前用户
	 */
	@Bean
	public AuditorAware<String> mongoAuditorProvider() {
		return new AuditorAware<String>() {

			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of("");
			}
		};
	}

	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

	@Bean
	public LoggingEventListener logginEventListener() {
		return new LoggingEventListener();
	}

}
