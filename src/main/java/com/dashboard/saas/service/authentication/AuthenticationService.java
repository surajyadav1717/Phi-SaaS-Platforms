package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.*;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    public RegisterResponseDTO registerUsers(RegisterRequestDTO request);

    public LoginResponseDTO loginUsers(LoginRequestDTO loginRequestDTO, HttpServletRequest request);

    public LoginResponseDTO refreshTokenExpiration(RefreshTokenRequestDTO request);

    public void logout(String refreshToken);

    public void sendOtp(String email, String otp);

}