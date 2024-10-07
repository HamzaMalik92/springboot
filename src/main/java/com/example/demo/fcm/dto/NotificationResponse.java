package com.example.demo.fcm.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

// Custom response class to encapsulate response data
public record NotificationResponse(String body, HttpStatus status) implements Serializable {}
