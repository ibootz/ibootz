package top.bootz.user.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;
import top.bootz.user.config.properties.TaskThreadPoolConfigProperties;

/**
 * 异步配置
 * 
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年6月24日 下午9:12:23
 */
@Slf4j
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig implements AsyncConfigurer {

	@Autowired
	private TaskThreadPoolConfigProperties configProperties;

	/**
	 * 自定义异步线程池
	 *
	 * @return
	 */
	@Bean
	@Primary
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix(configProperties.getThreadNamePrefix());
		executor.setCorePoolSize(configProperties.getCorePoolSize());
		executor.setKeepAliveSeconds(configProperties.getKeepAliveSeconds());
		executor.setMaxPoolSize(configProperties.getMaxPoolSize());
		executor.setQueueCapacity(configProperties.getQueueCapacity());

		// 设置拒绝策略
		executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor executor1) -> {
			log.error("TaskExecutor is rejected execute. activeCount [" + executor1.getActiveCount() + "] "
					+ "completedTaskCount [" + executor1.getCompletedTaskCount() + "] largestPoolSize ["
					+ executor1.getLargestPoolSize() + "] taskCount [" + executor1.getTaskCount() + "] ["
					+ executor1.getQueue().size() + "]");
			if (!executor1.isShutdown()) {
				r.run();
			}
		});

		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (Throwable arg0, Method arg1, Object... arg2) -> {
			log.error("An uncaught async exception has occurred. exception method [{}]", arg1.getName());
			log.error("", arg0);
		};
	}

}
