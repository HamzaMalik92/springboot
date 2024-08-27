package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Notification {
    @NotBlank
    @Size(min = 5)
    String title;

    Type type;


    @NotBlank
    @Size(min = 10)
    String description;
    String actionType="";
    String actionData="";
    String version = "";  // Default value set to an empty string

    @NotBlank
    @Size(min = 3)
    String topic;


}
