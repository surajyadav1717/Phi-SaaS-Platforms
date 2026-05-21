package com.dashboard.saas.dtos.authentication;

public class LoginResponseDTO {

    private String accessToken;

    private String tokenType = "Bearer";

    private Long userId;

    private String email;

    private String fullName;

    private String expirationTime;

    // DEFAULT CONSTRUCTOR
    public LoginResponseDTO() {
    }

    // GETTERS

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    // SETTERS

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String expirationTime() {
        return expirationTime;
    }

    public LoginResponseDTO setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }
}
