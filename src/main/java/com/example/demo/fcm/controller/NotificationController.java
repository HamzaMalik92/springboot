package com.example.demo.fcm.controller;

import com.example.demo.fcm.dto.Notification;
import com.example.demo.fcm.dto.NotificationResponse;
import com.example.demo.fcm.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/sendNotification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("all")
    public ResponseEntity<?> toAll(@Valid @RequestBody Notification notification, HttpSession session) {
        NotificationResponse notificationResponse = notificationService.sendToAll(notification);
        return new ResponseEntity<>(notificationResponse.getBody(), notificationResponse.getStatus());
    }

    @PostMapping("one/{uid}")
    public ResponseEntity<?> toOne(@PathVariable String uid, @Valid @RequestBody Notification notification, HttpSession session) {
        NotificationResponse notificationResponse = notificationService.sendToOne(uid, notification);
        return new ResponseEntity<>(notificationResponse.getBody(), notificationResponse.getStatus());
    }

    // Test API - Now delegates to the service layer
    @PostMapping("test/all")
    public ResponseEntity<?> toTestAll(@Valid @RequestBody Notification notification) {
        NotificationResponse notificationResponse = notificationService.testNotification(notification);
        return new ResponseEntity<>(notificationResponse.getBody(), notificationResponse.getStatus());
    }
}
