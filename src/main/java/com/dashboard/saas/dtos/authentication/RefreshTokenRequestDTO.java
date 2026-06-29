package com.dashboard.saas.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RefreshTokenRequestDTO {


    @NotBlank(message = "Refresh token is required")
    @Size(max = 500, message = "Refresh token is too long")
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
