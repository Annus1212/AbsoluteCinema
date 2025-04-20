package com.absolutecinema;

import io.github.cdimascio.dotenv.Dotenv; // Import Dotenv
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // Load .env file. If it doesn't exist, ignore the error.
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); 

        // Set system properties from .env variables for Spring Boot to pick up
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(Application.class, args);
    }
}