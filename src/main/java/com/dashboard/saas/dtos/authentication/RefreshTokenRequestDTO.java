package com.dashboard.saas.dtos.authentication;

public class RefreshTokenRequestDTO {


    private String RefreshToken;

    public RefreshTokenRequestDTO(String refreshToken) {
        RefreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }
}
