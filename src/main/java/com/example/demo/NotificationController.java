package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/sendNotification")
public class NotificationController {
    @PostMapping("all")
    public ResponseEntity<?> toAll(@Valid @RequestBody Notification notification) {

        if(notification.getVersion()!=null&&!notification.getVersion().isEmpty()&&notification.getVersion().length()!=3){
            return ResponseEntity.badRequest().body("Invalid app version");
        }

        String requestBody = getRequestBody("", notification);

        ResponseEntity<String> stringResponseEntity = SendToFCM(requestBody);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("requestBody", notification.toString());
        responseMap.put("responseBody", stringResponseEntity.getStatusCode().toString());

        // Return the map as the response
        return ResponseEntity.ok().body(responseMap);
    }


    @PostMapping("one/{uid}")
    public ResponseEntity<?> toOne(@PathVariable String uid, @Valid @RequestBody Notification notification) {

        if (notification.getVersion() != null && !notification.getVersion().isEmpty() && notification.getVersion().length() != 3) {
            return ResponseEntity.badRequest().body("Invalid app version");
        }
        if (uid == null || uid.length() != 36) {
            return ResponseEntity.badRequest().body("Invalid uId");
        }

        String requestBody = getRequestBody(uid, notification);

        ResponseEntity<String> stringResponseEntity = SendToFCM(requestBody);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("requestBody", notification.toString());
        responseMap.put("responseBody", stringResponseEntity.getStatusCode().toString());

        // Return the map as the response
        return ResponseEntity.ok().body(responseMap);
    }

    // Construct the request body using Notification object
    private static String getRequestBody(String uid, Notification notification) {
        String to = "/topics/" + notification.getTopic();
        return "{\n" +
                "    \"to\": \"" + to + "\",\n" +
                "    \"data\": {\n" +
                "        \"title\": \"" + notification.getTitle() + "\",\n" +
                "        \"description\": \"" + notification.getDescription() + "\",\n" +
                "        \"version\": \"" + notification.getVersion() + "\",\n" +
                "        \"actionData\": \"" + notification.getActionData() + "\",\n" +
                "        \"actionType\": \"" + notification.getActionType() + "\",\n" +
                "        \"type\": \"" + notification.getType().name() + "\",\n" +
                "        \"uid\": \"" + uid + "\"\n" +
                "        \"topic\": \"" + notification.getTopic() + "\"\n" +
                "    }\n" +
                "}";
    }

    private ResponseEntity<String> SendToFCM(String requestBody) {
        // FCM server endpoint URL
        String url = "https://fcm.googleapis.com/fcm/send";

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

        return response;
    }
}
