package me.namila.awssqsdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {
    @Bean(name = "MyCustomAsync")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(5);
        asyncTaskExecutor.setMaxPoolSize(5);
        asyncTaskExecutor.setQueueCapacity(10);
        asyncTaskExecutor.setThreadNamePrefix("SqsListener-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    @Bean(name = "MyCustomAsync2")
    public ThreadPoolTaskExecutor getAsyncExecutor1(@Value("${me.namila.sqs.aws.core-thread-count}") int coreThreadCount,
                                                    @Value("${me.namila.sqs.aws.max-thread-count}") int maxThreadCount,
                                                    @Value("${me.namila.sqs.aws.queue-capacity}") int queueCapacity) {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(coreThreadCount);
        asyncTaskExecutor.setMaxPoolSize(maxThreadCount);
        asyncTaskExecutor.setQueueCapacity(queueCapacity);
        asyncTaskExecutor.setThreadNamePrefix("MySQSThread-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

}
