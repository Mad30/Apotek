package com.example.apotek.Model;

public class Defaultresponse {

    private boolean error;
    private String Message;
    private Pesan data;
    private User user;

    public Pesan getData() {
        return data;
    }

    public User getUser() {
        return user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return Message;
    }
}
