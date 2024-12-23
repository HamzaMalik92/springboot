package com.example.demo.fcm.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

// Custom response record to encapsulate response data
// BELOW XML localName property are optional - just to change default names to new names
@Builder
@JacksonXmlRootElement(localName = "Response")
public record NotificationResponse(@JacksonXmlProperty(localName = "notification") Notification body, HttpStatus status) implements Serializable {}
