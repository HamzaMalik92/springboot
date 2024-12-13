package com.example.demo.fcm.config;

import com.example.demo.fcm.dto.Notification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(Notification notification) {
        return new RestTemplate();
    }

}
