package com.example.demo.fcm.controller;

import com.example.demo.fcm.dto.Notification;
import com.example.demo.fcm.dto.NotificationResponse;
import com.example.demo.fcm.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/sendNotification")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private final NotificationService notificationService;

    @PostMapping("all")
    public ResponseEntity<NotificationResponse> toAll(@Valid @RequestBody Notification notification, HttpSession session) {
        logger.info("Sending notification to all: {}", notification);
        NotificationResponse notificationResponse = notificationService.sendToAll(notification);
        return new ResponseEntity<>(notificationResponse, notificationResponse.status());
    }

    @PostMapping("one/{uid}")
    public ResponseEntity<NotificationResponse> toOne(@PathVariable String uid, @Valid @RequestBody Notification notification, HttpSession session) {
        logger.info("Sending notification to user {}: {}", uid, notification);
        NotificationResponse notificationResponse = notificationService.sendToOne(uid, notification);
        return new ResponseEntity<>(notificationResponse, notificationResponse.status());
    }

    // Test API - Now delegates to the service layer
    @PostMapping(value = "test/all" ,produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<NotificationResponse> toTestAll(@Valid @RequestBody Notification notification, HttpSession session) {
        logger.warn("Testing notification for all: {}", notification);
        NotificationResponse notificationResponse = notificationService.testNotification(notification);
        return new ResponseEntity<>(notificationResponse, notificationResponse.status());
    }
}
