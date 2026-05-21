package com.dashboard.saas.controllers.authentication;

import com.dashboard.saas.dtos.authentication.LoginRequestDTO;
import com.dashboard.saas.dtos.authentication.LoginResponseDTO;
import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;
import com.dashboard.saas.dtos.baseresponse.BaseAPIResponse;
import com.dashboard.saas.service.authentication.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new BaseAPIResponse<>("User Register Successfully",response, true);
    }

    @PostMapping("/login")
    public BaseAPIResponse<LoginResponseDTO> loginUsers(@RequestBody LoginRequestDTO request) {

        LoginResponseDTO response = authenticationService.loginUsers(request);
        return new BaseAPIResponse<>("User Login  Register Successfully",response, true);
    }


}
