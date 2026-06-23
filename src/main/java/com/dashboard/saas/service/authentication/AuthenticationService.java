package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.entities.Users;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AuthenticationService {

    public RegisterResponseDTO registerUsers(RegisterRequestDTO request);

  public OtpResponseDTO loginUsers(LoginRequestDTO loginRequestDTO, HttpServletRequest request);

    public LoginResponseAccessToken refreshTokenExpiration(RefreshTokenRequestDTO request);

    public void logout(String refreshToken);

    public LoginResponseDTO verifyOtp(VerifyOtpRequestDTO request,HttpServletRequest httpServletRequest);

    public OtpResponseDTO resendOtp(ResendOtpRequestDTO request);

     public Users getUser(Long userId)  ;

    public Users updateUser(Long userId, UpdateUserRequestDTO request);

    List<ActiveSessionDTO> getActiveSessions(Long userId);
}