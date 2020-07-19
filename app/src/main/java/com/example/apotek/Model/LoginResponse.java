package com.example.apotek.Model;

public class LoginResponse {

    private boolean error;
    private String message;
    private User user;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
