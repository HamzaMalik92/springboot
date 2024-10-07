package com.example.demo.fcm.service;

import com.example.demo.fcm.dto.Notification;
import com.example.demo.fcm.dto.NotificationResponse;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.demo.fcm.constants.Constants.*;

@Service
public class NotificationService {

    public NotificationResponse sendToAll(Notification notification) {
        String requestBody = getRequestBody("", notification);
        return new NotificationResponse(notification.toString(), (HttpStatus) sendToFCM(requestBody).getStatusCode());

    }

    public NotificationResponse sendToOne(String uid, Notification notification) {
        if (uid == null || uid.length() != 36) {
            return new NotificationResponse("Invalid uId", HttpStatus.BAD_REQUEST);
        }

        String requestBody = getRequestBody(uid, notification);
        return new NotificationResponse(notification.toString(), (HttpStatus) sendToFCM(requestBody).getStatusCode());
    }

    public NotificationResponse testNotification(Notification notification) {
        // For testing purposes, simply return the notification object
        return new NotificationResponse(notification.toString(),HttpStatus.ACCEPTED);
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

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        System.out.println("Response: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

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

