package com.sachin.bean;


public class ChatMessage {
    private String username;
    private String message;

    // Default constructor (required for Jackson)
    public ChatMessage() {
    }

    // Constructor with parameters
    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

