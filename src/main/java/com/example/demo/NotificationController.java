package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NotificationController {
    @PostMapping("sendNotification")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody Notification notification) {

        if(notification.getVersion()!=null&&!notification.getVersion().isEmpty()&&notification.getVersion().length()!=3){
            return ResponseEntity.ok().body("Invalid app version");
        }

        // FCM server endpoint URL
        String url = "https://fcm.googleapis.com/fcm/send";
        // FCM request data
        String to = "/topics/"+notification.getTopic();

        // Construct the request body using Notification object
        String requestBody = "{\n" +
                "    \"to\": \"" + to + "\",\n" +
                "    \"data\": {\n" +
                "        \"title\": \"" + notification.getTitle() + "\",\n" +
                "        \"description\": \"" + notification.getDescription() + "\",\n" +
                "        \"version\": \"" + notification.getVersion() + "\",\n" +
                "        \"type\": \"" + notification.getType().name() + "\",\n" +
                "     }\n" +
                "}";

        // Set headers

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=AAAAUfOZKes:APA91bE_5j8k0UvN7gVZb-bdQONtCnXD95zqP875bDt9vhpq5z6K7bbiM_aiYmyhojIC_itVIff9v-ZXn94Ib94xIjGWHlVAP6hbsjE1LMavYWQFxGobMPYSUuJiUKzKpL-TnuxiRfIQ");

        // Create the request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Print response
        System.out.println("Response: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        return ResponseEntity.ok().body(notification);
    }
}
