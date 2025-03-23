package com.example.tpo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FlashcardsApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        Environment env = context.getEnvironment();
        String activeProfile = env.getProperty("spring.profiles.active");
        System.out.println("Active Profile: " + activeProfile);

        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.run();
    }
}
