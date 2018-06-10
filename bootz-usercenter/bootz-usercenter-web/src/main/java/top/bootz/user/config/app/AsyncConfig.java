package top.bootz.user.config.app;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {

	/**
	 * 自定义异步线程池
	 * 
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public AsyncTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("App-Executor");
		executor.setCorePoolSize(5);
		executor.setKeepAliveSeconds(120);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(30);
		// 设置拒绝策略
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				log.error("Task is rejected execute. activeCount [" + executor.getActiveCount() + "] "
						+ "completedTaskCount [" + executor.getCompletedTaskCount() + "] largestPoolSize ["
						+ executor.getLargestPoolSize() + "] taskCount [" + executor.getTaskCount() + "] ["
						+ executor.getQueue().size() + "]");
				if (!executor.isShutdown()) {
					r.run();
				}
			}
		});
		return executor;
	}

}
