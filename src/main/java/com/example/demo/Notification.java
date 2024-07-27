package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Notification {
    @NotBlank
    @Size(min = 5)
    String title;

    Type type;
    @NotBlank
    @Size(min = 10)
    String description;


    public String getTopic() {
        return topic;
    }

    public Notification(String title, Type type, String description, String version, String topic) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.version = version;
        this.topic = topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    String version = "";  // Default value set to an empty string

    @NotBlank
    @Size(min = 3)
    String topic;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
