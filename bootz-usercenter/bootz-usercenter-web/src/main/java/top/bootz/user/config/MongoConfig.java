package top.bootz.user.config;

//@Configuration
//@EnableMongoRepositories(basePackages = { "top.bootz.user.service.mongo" })
//@EnableMongoAuditing(auditorAwareRef = "mongoAuditorProvider", dateTimeProviderRef = "mongoDateTimeProvider", modifyOnCreate = false)
public class MongoConfig {
//
//	/**
//	 * 添加操作时间审计
//	 *
//	 * @return DateTimeProvider
//	 */
//	@Bean
//	public DateTimeProvider mongoDateTimeProvider() {
//		return CurrentDateTimeProvider.INSTANCE;
//	}
//
//	/**
//	 * 添加操作人审计 <br/>
//	 */
//	@Bean
//	public AuditorAware<String> mongoAuditorProvider() {
//		// TODO 暂时没有实现登录授权功能,所以这里暂时没法获取当前用户
//		return new AuditorAware<String>() {
//
//			@Override
//			public Optional<String> getCurrentAuditor() {
//				return Optional.of("TODO");
//			}
//		};
//	}
//
//	@Bean
//	public MongoExceptionTranslator exceptionTranslator() {
//		return new MongoExceptionTranslator();
//	}
//
//	@Bean
//	@ConditionalOnMissingBean
//	public LoggingEventListener logginEventListener() {
//		return new LoggingEventListener();
//	}
//
}
