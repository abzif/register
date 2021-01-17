package com.example.register;

import com.example.register.service.RegisterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

    /*
     * this function will be automatically run upon spring application start
     * because it returns CommandLineRunner
     */
    @Bean
    public CommandLineRunner demoScenario(RegisterService service) {
        return new RegisterCmdLineRunner(service);
    }
}
