package com.polixis.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
@Log4j2
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TaskApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.addActiveProfile("messageGenerator");
        application.setEnvironment(environment);
        application.setRegisterShutdownHook(true);
        ConfigurableApplicationContext context = application.run(args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down...");
            context.close();
        }));
        log.info("Task Application started...");
    }

}
