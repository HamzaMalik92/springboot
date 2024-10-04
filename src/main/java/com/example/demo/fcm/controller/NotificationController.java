package com.example.demo.fcm.controller;

import com.example.demo.fcm.dto.Notification;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ServiceAccountCredentials;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.fcm.constants.Constants.*;

@RestController()
@RequestMapping("/sendNotification")
public class NotificationController {

    @PostMapping("all")
    public ResponseEntity<?> toAll(@Valid @RequestBody Notification notification, HttpSession session) {

        String requestBody = getRequestBody("", notification);

        ResponseEntity<String> stringResponseEntity = SendToFCM(requestBody);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("requestBody", notification.toString());
        responseMap.put("responseBody", stringResponseEntity.getStatusCode().toString());

        // Return the map as the response
        return ResponseEntity.ok().body(responseMap);
    }

    @PostMapping("test/all")
    public ResponseEntity<?> toTestAll(@Valid @RequestBody Notification notification, HttpSession session) {

        // Return the map as the response
        return ResponseEntity.ok().body(notification);
    }


    @PostMapping("one/{uid}")
    public ResponseEntity<?> toOne(@PathVariable String uid, @Valid @RequestBody Notification notification, HttpSession session) {

        if (uid == null || uid.length() != 36) {
            return ResponseEntity.badRequest().body("Invalid uId");
        }

        String requestBody = getRequestBody(uid, notification);

        ResponseEntity<String> stringResponseEntity = SendToFCM(requestBody);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("requestBody", notification.toString());
        responseMap.put("responseBody", stringResponseEntity.getStatusCode().toString());

        return ResponseEntity.ok().body(responseMap);
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

    private ResponseEntity<String> SendToFCM(String requestBody) {
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
