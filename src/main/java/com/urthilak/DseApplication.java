package com.urthilak;

import com.urthilak.service.DseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.urthilak.util.QuoteUtil.createQuote;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        BatchAutoConfiguration.class,
})
public class DseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DseApplication.class, args);

        DseService dseService = context.getBean(DseService.class);
        TaskExecutor taskExecutor = context.getBean(TaskExecutor.class);

        createQuote(10000000).forEach(message -> taskExecutor.execute(new TaskRunner(dseService, message)));
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(1000000);
        return executor;
    }
}
