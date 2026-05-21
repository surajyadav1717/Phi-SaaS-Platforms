package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.LoginRequestDTO;
import com.dashboard.saas.dtos.authentication.LoginResponseDTO;
import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;

public interface AuthenticationService {

    public RegisterResponseDTO  registerUsers(RegisterRequestDTO request);

    public LoginResponseDTO loginUsers(LoginRequestDTO loginRequestDTO);
}
