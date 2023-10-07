package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NotificationController {
    @PostMapping("sendNotification")
    public HttpStatusCode sendNotification(@RequestBody Notification notification) {
        // FCM server endpoint URL
        String url = "https://fcm.googleapis.com/fcm/send";

        // FCM request data
        String to = "/topics/app";

        // Construct the request body using Notification object
        String requestBody = "{\n" +
                "    \"to\": \"" + to + "\",\n" +
                "    \"data\": {\n" +
                "        \"title\": \"" + notification.getTitle() + "\",\n" +
                "        \"description\": \"" + notification.getDescription() + "\",\n" +
                "        \"type\":"+ notification.getType() +"\n" +
                "     }\n" +
                "}";

        // Set headers

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=AAAA7PPMAfM:APA91bETH64DmjvloDsSxsNqFSRFr4f791pUUWBsQFT8E_fhDpQBDld63-lnMDf8fjResKiq0V_VMoOOmCHMemuOTbOvEOCYu1UQiR9ucCpd0h9r_z6xZOqscuOumNm9WJEoRI7g6lQw");

        // Create the request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        System.out.println("Response: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        return response.getStatusCode();
    }
}
