package top.bootz.user.config.springdata;

import java.util.Optional;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"top.bootz.user.biz.persist.mysql" }, entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "transactionManager")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
public class JpaConfig {

	@Autowired
	private DataSource dataSource;

	/**
	 * JPA实体转换器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public JpaVendorAdapter vendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		return vendorAdapter;
	}

	/**
	 * JPA实体管理器工厂
	 * 
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public EntityManagerFactory userEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter());
		factory.setPersistenceProvider(new HibernatePersistenceProvider());
		factory.setPackagesToScan("top.bootz.user.biz.entity.mysql");
		factory.setDataSource(dataSource);
		factory.setJpaDialect(new HibernateJpaDialect());
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
		properties.put("hibernate.show_sql", false);
		properties.put("hibernate.format_sql", false);
		properties.put("hibernate.ejb.entitymanager_factory_name", "entityManagerFactory");
		properties.put("hibernate.hbm2ddl.auto", "none"); // 自动建表
		properties.put("hibernate.dialect.storage_engine", "innodb"); // 指定引擎
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	/**
	 * 配置JPA事务管理器
	 * 
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(userEntityManagerFactory());
		return transactionManager;
	}

	/**
	 * 把HibernateExceptions转换成DataAccessExceptions
	 */
	@Bean
	@ConditionalOnMissingBean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	/**
	 * 添加操作时间审计
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public DateTimeProvider dateTimeProvider() {
		return CurrentDateTimeProvider.INSTANCE;
	}

	/**
	 * 添加操作人审计
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuditorAware<String> auditorProvider() {
		// TODO 暂未填充操作人的审计信息
		return new AuditorAware<String>() {

			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of("");
			}

		};
	}

}
