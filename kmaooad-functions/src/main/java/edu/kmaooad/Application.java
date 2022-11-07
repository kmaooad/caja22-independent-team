package edu.kmaooad;

import org.springframework.boot.SpringApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        ApiContextInitializer.init();

        SpringApplication.run(Application.class, args);
    }
}
