package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;

public interface AuthenticationService {

    public RegisterResponseDTO  registerUsers(RegisterRequestDTO request);
}
