package me.namila.awssqsdemo.config;

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
        asyncTaskExecutor.setCorePoolSize(2);
        asyncTaskExecutor.setMaxPoolSize(5);
        asyncTaskExecutor.setQueueCapacity(10);
        asyncTaskExecutor.setThreadNamePrefix("MyCustomThread-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

    @Bean(name = "MyCustomAsync2")
    public ThreadPoolTaskExecutor getAsyncExecutor1() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setCorePoolSize(5);
        asyncTaskExecutor.setMaxPoolSize(10);
        asyncTaskExecutor.setQueueCapacity(10);
        asyncTaskExecutor.setThreadNamePrefix("MySQSThread-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }

}
