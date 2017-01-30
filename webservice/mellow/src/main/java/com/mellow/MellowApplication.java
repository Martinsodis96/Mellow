package com.mellow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MellowApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MellowApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MellowApplication.class);
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}