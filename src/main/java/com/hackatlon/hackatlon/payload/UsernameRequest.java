package com.hackatlon.hackatlon.payload;

public class UsernameRequest {

    private String username;

    public UsernameRequest(String username) {
        this.username = username;
    }

    public UsernameRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}