package top.bootz.commons.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class SpringHelper {

	private static final Logger LOGGER = LogManager.getLogger(SpringHelper.class);

	private ApplicationContext applicationContext;

	private SpringHelper() {
	}

	private static class SingletonHolder {
		private SingletonHolder() {
		}

		private static SpringHelper beanUtil = new SpringHelper();
	}

	public static SpringHelper getInstance() {
		return SingletonHolder.beanUtil;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void close() {
		this.applicationContext = null;
	}

	/**
	 * 容器启动时，会将applicationContext注入到当前类中。如果是Junit测试，需要手动注入才能使用
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		ApplicationContext ac = SpringHelper.getInstance().getApplicationContext();
		if (ac == null) {
			LOGGER.error("failed inject applicationContext into SpringUtil");
			throw new RuntimeException("Failed inject applicationContext into SpringUtil");
		}
		return ac.getBean(beanName);
	}

}
