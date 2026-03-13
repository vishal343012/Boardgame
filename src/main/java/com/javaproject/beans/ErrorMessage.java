package com.javaproject.beans;

public class ErrorMessage {
    private String message;

    // No-argument constructor (already exists)
    public ErrorMessage() {}

    // New constructor to accept message
    public ErrorMessage(String message) {
        this.message = message;
    }

    // Getter and setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
