package com.example.demo;

public class Notification {
    String title;



    boolean type;
    String description;


    public Notification(String title, boolean type, String description) {
        this.title = title;
        this.type = type;
        this.description = description;
    }

    public void setType(boolean type) {
        this.type = type;
    }
    public boolean getType() {
        return type;
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
