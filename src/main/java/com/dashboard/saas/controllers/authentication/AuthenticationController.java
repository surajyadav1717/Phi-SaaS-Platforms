package com.dashboard.saas.controllers.authentication;

import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Handler;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public BaseAPIResponse<RegisterResponseDTO> registerUsers(@RequestBody RegisterRequestDTO request) {

        RegisterResponseDTO response = authenticationService.registerUsers(request);
        return new BaseAPIResponse<>("User Register Successfully", response, true);
    }

    @PostMapping("/login")
    public BaseAPIResponse<LoginResponseDTO> loginUsers(@RequestBody LoginRequestDTO request , HttpServletRequest httpServletRequest) {

        LoginResponseDTO response = authenticationService.loginUsers(request,httpServletRequest);
        return new BaseAPIResponse<>("User Login  Successfully", response, true);
    }


    @PostMapping("/refresh-token")
    public BaseAPIResponse<LoginResponseDTO> refreshToken(
            @RequestBody RefreshTokenRequestDTO request
    ) {

        LoginResponseDTO response =
                authenticationService.refreshTokenExpiration(request);

        return new BaseAPIResponse<>(
                "Access Token Refreshed Successfully",
                response,
                true
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
}




