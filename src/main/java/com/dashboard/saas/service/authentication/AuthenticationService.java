package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.*;

public interface AuthenticationService {

    public RegisterResponseDTO registerUsers(RegisterRequestDTO request);

    public LoginResponseDTO loginUsers(LoginRequestDTO loginRequestDTO);

    public LoginResponseDTO refreshTokenExpiration(RefreshTokenRequestDTO request);

    public void logout(String refreshToken);
}