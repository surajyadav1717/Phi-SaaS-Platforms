package com.dashboard.saas.dtos.authentication;

import java.time.LocalDateTime;
import java.util.Date;

public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private String email;

    private String fullName;

    private Date expiresAt;

    // DEFAULT CONSTRUCTOR

    public LoginResponseDTO() {
    }


    // GETTERS

    public String getAccessToken() {
        return accessToken;
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


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
