package com.dashboard.saas.controllers.authentication;

import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.entities.AuditLog;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.service.AuditLogService;
import com.dashboard.saas.service.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final AuditLogService auditLogService;

    public AuthenticationController(AuthenticationService authenticationService, AuditLogService auditLogService) {
        this.authenticationService = authenticationService;
        this.auditLogService = auditLogService;
    }


    @PostMapping("/register")
    public BaseAPIResponse<RegisterResponseDTO> registerUsers(@RequestBody RegisterRequestDTO request) {

        RegisterResponseDTO response = authenticationService.registerUsers(request);
        return new BaseAPIResponse<>("User Register Successfully", response, true);
    }

    @PostMapping("/login")
    public BaseAPIResponse<OtpResponseDTO> loginUsers(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {

        OtpResponseDTO response = authenticationService.loginUsers(loginRequestDTO,request);
        return new BaseAPIResponse<>("OTP Send Successfully -Verify Through Redis ", response, true);
    }




    @PostMapping("/refresh-token")
    public BaseAPIResponse<LoginResponseAccessToken> refreshToken(
            @RequestBody RefreshTokenRequestDTO request
    ) {

        LoginResponseAccessToken response =
                authenticationService.refreshTokenExpiration(request);

        return new BaseAPIResponse<>(
                "Access Token Refreshed Succ  essfully",
                response,
                true
        );
    }

    @PostMapping("/verify-otp")
    public BaseAPIResponse<LoginResponseDTO> verifyOtp(@RequestBody VerifyOtpRequestDTO request, HttpServletRequest httpServletRequest) {

        LoginResponseDTO response = authenticationService.verifyOtp(request, httpServletRequest);

        return new BaseAPIResponse<>("Login Successfully", response, true
        );
    }


    @PostMapping("/logout")
    public BaseAPIResponse<String> logout(
            @RequestBody RefreshTokenRequestDTO request
    ) {

        authenticationService.logout(
                request.getRefreshToken() // refresh token revoked
        );

        return new BaseAPIResponse<>(
                "Logout Successfully",
                "Refresh Token Revoked",
                true
        );
    }

    @PostMapping("/resend-otp")
    public BaseAPIResponse<OtpResponseDTO>
    resendOtp(
            @RequestBody
            ResendOtpRequestDTO request
    ){

        OtpResponseDTO response =
                authenticationService
                        .resendOtp(
                                request
                        );

        return new BaseAPIResponse<>(
                "OTP Resent Successfully",
                response,
                true
        );
    }


    @GetMapping("/user/{id}")
    public Users getUser(@PathVariable Long id) throws Exception {

        return authenticationService.getUser(id);
    }

    @PutMapping("/update-user/{userId}")
    public Users updateUser(@PathVariable Long userId,
                            @RequestBody UpdateUserRequestDTO request) {

        return authenticationService.updateUser(userId, request);
    }


    @GetMapping("/sessions/{userId}")
    public BaseAPIResponse<List<ActiveSessionDTO>> getActiveSessions(@PathVariable Long userId) {

        List<ActiveSessionDTO> response = authenticationService.getActiveSessions(userId);

        return new BaseAPIResponse<>("Active Sessions Fetched Successfully", response, true
        );
    }



}





