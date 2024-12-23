package com.example.demo.fcm.service;

import com.example.demo.fcm.dto.Notification;
import com.example.demo.fcm.dto.NotificationResponse;
import com.example.demo.fcm.repo.NotificationRepo;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.demo.fcm.constants.Constants.*;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    private RestTemplate restTemplate;

    public NotificationResponse sendToAll(Notification notification) {
        String requestBody = getRequestBody("", notification);
        return new NotificationResponse(notification, (HttpStatus) sendToFCM(requestBody).getStatusCode());

    }

    public NotificationResponse sendToOne(String uid, Notification notification) {
        if (uid == null || uid.length() != 36) {
            throw new IllegalArgumentException("Invalid Uid: UID must be 36 characters long.");
        }

        String requestBody = getRequestBody(uid, notification);
        return new NotificationResponse(notification, (HttpStatus) sendToFCM(requestBody).getStatusCode());
    }
    @Cacheable(value = "notificationCache", key = "#notification.id")
    public NotificationResponse testNotification(Notification notification) {
        notificationRepo.save(notification);
        // For testing purposes, simply return the notification object
        return NotificationResponse.builder().body(notification).status(HttpStatus.ACCEPTED).build();
    }

    // Construct the request body using Notification object
    private static String getRequestBody(String uid, Notification notification) {
        return "{\n" +
                "  \"message\": {\n" +
                "    \"topic\": \"" + notification.getTopic() + "\",\n" +
                "    \"data\": {\n" +
                "        \"title\": \"" + notification.getTitle() + "\",\n" +
                "        \"description\": \"" + notification.getDescription() + "\",\n" +
                "        \"version\": \"" + notification.getVersion() + "\",\n" +
                "        \"actionData\": \"" + notification.getActionData() + "\",\n" +
                "        \"actionType\": \"" + notification.getActionType() + "\",\n" +
                "        \"type\": \"" + notification.getType().name() + "\",\n" +
                "        \"uid\": \"" + uid + "\",\n" +
                "        \"topic\": \"" + notification.getTopic() + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private ResponseEntity<String> sendToFCM(String requestBody) {
        // FCM server endpoint URL
        String url = String.format(FCM_URL_TEMPLATE, FCM_PROJECT_NAME);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAccessToken());

        // Create the request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        logger.info("Response: {}" , response.getStatusCode());
        logger.info("Response Body: {}" , response.getBody());

        return response;
    }

    private String getAccessToken() {
        try (FileInputStream serviceAccountStream = new FileInputStream(FCM_SERVICE_JSON_PATH)) {
            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
            credentials = credentials.createScoped("https://www.googleapis.com/auth/cloud-platform");
            credentials.refreshIfExpired();
            AccessToken token = credentials.getAccessToken();
            return token.getTokenValue();
        } catch (IOException e) {
            throw new RuntimeException("Error obtaining OAuth2 access token", e);
        }
    }
}

