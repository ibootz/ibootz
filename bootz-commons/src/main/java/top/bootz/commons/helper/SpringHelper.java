package top.bootz.commons.helper;

import org.springframework.context.ApplicationContext;

import com.google.common.base.Preconditions;

public class SpringHelper {

	private SpringHelper() {
	}

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		Preconditions.checkNotNull(applicationContext, "ApplicationContext must not be null");
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) getApplicationContext().getBean(beanName);
	}

}
