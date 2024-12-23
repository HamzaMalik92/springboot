package com.example.demo.fcm.dto;

import com.example.demo.fcm.emun.Type;
import com.example.demo.fcm.validator.ValidAppVersion;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Notification implements Serializable {
    @Id
    private String id = UUID.randomUUID().toString();  // Generate ID upon object creation
    @NotBlank(message = "Title must not be blank")
    @Size(min = 5, max = 20, message = "Title must be between 5 and 20 characters")
    String title;

    Type type;


    @NotBlank(message = "Description must not be blank")
    @Size(min = 10, max = 100)
    @JacksonXmlProperty(localName = "body")
    String description;
    String actionType="";
    String actionData="";
    @ValidAppVersion(message = "You have entered invalid app version!")
    String version = "";  // Default value set to an empty string

    @NotBlank
    @Size(min = 3)
    String topic;


}
