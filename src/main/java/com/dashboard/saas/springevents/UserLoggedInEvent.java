package com.dashboard.saas.springevents;

public class UserLoggedInEvent {

    private Long userId;

    private String email;

    private String ipAddress;

    public UserLoggedInEvent() {
    }

    public UserLoggedInEvent(Long userId, String email, String ipAddress) {
        this.userId = userId;
        this.email = email;
        this.ipAddress = ipAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
