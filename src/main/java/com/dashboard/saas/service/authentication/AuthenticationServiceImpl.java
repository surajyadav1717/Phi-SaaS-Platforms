package com.dashboard.saas.service.authentication;

import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.entities.RefreshToken;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.repositories.RefreshTokenRepository;
import com.dashboard.saas.repositories.UserRepository;
import com.dashboard.saas.security.JwtTokenProvider;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Value("${app.jwt.refresh-token-expiration-ms}")
    private Long refreshTokenExpiration;

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
                .findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        // STEP 2 → MATCH PASSWORD
        boolean passwordMatched =
                passwordEncoder.matches(
                        loginRequestDTO.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatched) {
            throw new RuntimeException("Invalid Password");
        }

        // STEP 3 → GENERATE ACCESS TOKEN
        String accessToken =
                jwtTokenProvider.generateToken(
                        user.getId(),
                        user.getEmail(),
                        user.getName()
                );

        // STEP 4 → GENERATE REFRESH TOKEN
//        String refreshToken =
//                jwtTokenProvider.generateRefreshToken(
//                        user.getId()
//                );

        // STEP 5 → SAVE REFRESH TOKEN IN DB

        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshTokenEntity =
                new RefreshToken();

        refreshTokenEntity.setRefreshToken(refreshToken);
        refreshTokenEntity.setUser(user);

//        refreshTokenEntity.setExpiryDate(
//                LocalDateTime.now().plusDays(7)
//        );
        refreshTokenEntity.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(
                                refreshTokenExpiration / 1000
                        )
        );
        refreshTokenEntity.setRevoked(false);

        refreshTokenRepository.save(refreshTokenEntity);

        // STEP 6 → GET ACCESS TOKEN EXPIRY
        Date expiry =
                jwtTokenProvider.getTokenExpiration(accessToken);

        // STEP 7 → PREPARE RESPONSE
        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getName());
        response.setExpiresAt(expiry);

        return response;
    }
   // @Override
//    public LoginResponseDTO refreshToken(LoginRequestDTO loginRequestDTO) {
//
//        Optional<Users> user = Optional.ofNullable(userRepository.findByEmail(loginRequestDTO.getEmail())
//                .orElseThrow(() -> new RuntimeException("Invalid Email")));
//
//        Users users = new Users(Optional.of(user.get()));
//
//
//        boolean passwordMatches =
//                passwordEncoder.matches(
//                        loginRequestDTO.getPassword(),
//                        users.getPassword()
//                );
//
//        if (!passwordMatches) {
//
//            throw new RuntimeException("Invalid Password");
//        }
//        ;
//
//        String accessToken = jwtTokenProvider.generateToken(
//                users.getId(),
//                users.getEmail(),
//                users.getName()
//        );
//
//        String refreshToken = jwtTokenProvider.generateRefreshToken(
//                user.get().getId()
//
//        );
//
//        RefreshToken refreshTokenEntity = new RefreshToken();
//        refreshTokenEntity.setRefresh_token(refreshToken);
//        refreshTokenEntity.setUser(users);
//        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
//
//        refreshTokenEntity.setRevoked(false);
//        refreshTokenRepository.save(refreshTokenEntity);
//
//
//        LoginResponseDTO  responseDTO = new LoginResponseDTO();
//
//        responseDTO.setAccessToken(accessToken);
//        responseDTO.setRefreshToken(refreshToken);
//
//
//        responseDTO.setExpiresAt(
//                jwtTokenProvider.getTokenExpiration(accessToken)
//        );
//        responseDTO.setUserId(users.getId());
//        responseDTO.setEmail(users.getEmail());
//        responseDTO.setFullName(users.getName());
//        return responseDTO;
//    }

    @Override
    public LoginResponseDTO refreshTokenExpiration(RefreshTokenRequestDTO request) {

        Optional<RefreshToken> refreshTokenEntity = Optional.ofNullable(refreshTokenRepository
                .findByRefreshToken(request.getRefreshToken()
                ).orElseThrow(() -> new RuntimeException("Refresh Token Not Found")));

        if (refreshTokenEntity.get().getRevoked()) {

            throw new RuntimeException("Refresh Token Revoked");
        }

        if(refreshTokenEntity.get().getExpiryDate().isBefore(LocalDateTime.now())){

            throw  new RuntimeException("Refresh Token Expired");
        }

//        boolean  valid=jwtTokenProvider.validateToken(
//                request.getRefreshToken()
//        );
//
//        if(!valid){
//
//            throw new RuntimeException("Invalid Refresh Token");
//        }

        Users user =
                refreshTokenEntity.get().getUser();

        String newAccessToken =
                jwtTokenProvider.generateToken(
                        user.getId(),
                        user.getEmail(),
                        user.getName()
                );

        LoginResponseDTO responseDTO = new LoginResponseDTO();

        responseDTO.setAccessToken(newAccessToken);
        responseDTO.setRefreshToken(request.getRefreshToken());
        responseDTO.setExpiresAt(jwtTokenProvider.getTokenExpiration(newAccessToken));
        responseDTO.setUserId(user.getId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setFullName(user.getName());
        return responseDTO;
    }

    @Override
    public void logout(String refreshToken) {

        RefreshToken refreshTokenLogout =refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->
                        new RuntimeException("Refresh Token Not Found"));

        refreshTokenLogout.setRevoked(true);
        refreshTokenRepository.save(refreshTokenLogout);
    }
}

