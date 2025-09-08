package com.example.houseentry.dto;

public class PasswordResponse {
    private long password;
    private String status;

    public PasswordResponse() {}

    public PasswordResponse(long password, String status) {
        this.password = password;
        this.status = status;
    }

    public long getPassword() { return password; }
    public void setPassword(long password) { this.password = password; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
