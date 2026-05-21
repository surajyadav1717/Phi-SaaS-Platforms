package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.LoginRequestDTO;
import com.dashboard.saas.dtos.authentication.LoginResponseDTO;
import com.dashboard.saas.dtos.authentication.RegisterRequestDTO;
import com.dashboard.saas.dtos.authentication.RegisterResponseDTO;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.repositories.UserRepository;
import com.dashboard.saas.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider ;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Override
    public LoginResponseDTO loginUsers(
            LoginRequestDTO loginRequestDTO
    ) {

        // STEP 1 → FIND USER
        Users user = userRepository
                .findByEmail(loginRequestDTO.email())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        // STEP 2 → MATCH PASSWORD
        boolean passwordMatched =
                passwordEncoder.matches(
                        loginRequestDTO.password(),
                        user.getPassword()
                );

        if (!passwordMatched) {
            throw new RuntimeException("Invalid Password");
        }

        // STEP 3 → GENERATE TOKEN
        String accessToken =
                jwtTokenProvider.generateToken(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.
                );

        // STEP 4 → PREPARE RESPONSE
        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setAccessToken(accessToken);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getName());
        response.setExpirationTime(user.get)

        return response;
    }
}
