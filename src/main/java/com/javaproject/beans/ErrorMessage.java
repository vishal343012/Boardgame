package com.javaproject.beans;

public class ErrorMessage {

    private String message;

    // No-argument constructor
    public ErrorMessage() {
    }

    // Constructor with a message
    public ErrorMessage(String message) {
        this.message = message;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }
}
