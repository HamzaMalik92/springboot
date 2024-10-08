package com.example.demo.fcm.controller;

import com.example.demo.fcm.dto.Notification;
import com.example.demo.fcm.dto.NotificationResponse;
import com.example.demo.fcm.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/sendNotification")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @PostMapping("all")
    public ResponseEntity<String> toAll(@Valid @RequestBody Notification notification, HttpSession session) {
        logger.info("Sending notification to all: {}", notification);
        NotificationResponse notificationResponse = notificationService.sendToAll(notification);
        return new ResponseEntity<>(notificationResponse.body(), notificationResponse.status());
    }

    @PostMapping("one/{uid}")
    public ResponseEntity<String> toOne(@PathVariable String uid, @Valid @RequestBody Notification notification, HttpSession session) {
        logger.info("Sending notification to user {}: {}", uid, notification);
        NotificationResponse notificationResponse = notificationService.sendToOne(uid, notification);
        return new ResponseEntity<>(notificationResponse.body(), notificationResponse.status());
    }

    // Test API - Now delegates to the service layer
    @PostMapping("test/all")
    public ResponseEntity<String> toTestAll(@Valid @RequestBody Notification notification) {
        logger.info("Testing notification for all: {}", notification);
        NotificationResponse notificationResponse = notificationService.testNotification(notification);
        return new ResponseEntity<>(notificationResponse.body(), notificationResponse.status());
    }
}
