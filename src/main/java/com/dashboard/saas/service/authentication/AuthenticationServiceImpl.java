package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public RegisterResponseDTO registerUsers(RegisterRequestDTO request) {


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new RuntimeException("Mobile number already exists");
        }

        Users users = new Users();

        users.setName(request.getName());
        users.setEmail(request.getEmail());
        users.setMobileNumber(request.getMobileNumber());
        users.setPassword(passwordEncoder.encode(request.getPassword()));

        Users savedUser = userRepository.save(users);

        RegisterResponseDTO response = new RegisterResponseDTO();

        response.setUserId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setMobileNumber(savedUser.getMobileNumber());

        return response;


    }
}
