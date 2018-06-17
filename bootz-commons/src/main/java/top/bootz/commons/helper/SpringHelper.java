package top.bootz.commons.helper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringHelper {

	private SpringHelper() {
	}

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new FileSystemXmlApplicationContext(new String[0]);
		}
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

}
