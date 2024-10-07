package com.example.demo.fcm.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Custom response class to encapsulate response data
@Data
public class NotificationResponse {
    private final String body;
    private final HttpStatus status;
}
