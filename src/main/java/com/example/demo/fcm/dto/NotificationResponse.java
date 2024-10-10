package com.example.demo.fcm.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

// Custom response record to encapsulate response data
@Builder
public record NotificationResponse(String body, HttpStatus status) implements Serializable {}
