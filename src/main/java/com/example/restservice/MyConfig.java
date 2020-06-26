package com.example.restservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public GreetingGenerator getDependency() {
        return new GreetingGenerator();
    }
}
