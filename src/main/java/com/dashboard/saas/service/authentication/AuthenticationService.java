package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.entities.Users;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    public RegisterResponseDTO registerUsers(RegisterRequestDTO request);

  public OtpResponseDTO loginUsers(LoginRequestDTO loginRequestDTO, HttpServletRequest request);

    public LoginResponseAccessToken refreshTokenExpiration(RefreshTokenRequestDTO request);

    public void logout(String refreshToken);

    public LoginResponseDTO verifyOtp(VerifyOtpRequestDTO request,HttpServletRequest httpServletRequest);

    public OtpResponseDTO resendOtp(ResendOtpRequestDTO request);

     public Users getUser(Long userId) throws Exception ;
}