package com.dashboard.saas.service.authentication;
import com.dashboard.saas.dtos.authentication.*;
import com.dashboard.saas.entities.EmailOtp;
import com.dashboard.saas.entities.RefreshToken;
import com.dashboard.saas.entities.Users;
import com.dashboard.saas.repositories.EmailRepository;
import com.dashboard.saas.repositories.RefreshTokenRepository;
import com.dashboard.saas.repositories.UserRepository;
import com.dashboard.saas.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JavaMailSender javaMailSender;

    private final EmailRepository emailRepository;

    private final RedisOtpService redisOtpService;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;


    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository, JavaMailSender javaMailSender, EmailRepository emailRepository, RedisOtpService redisOtpService, RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.javaMailSender = javaMailSender;
        this.emailRepository = emailRepository;
        this.redisOtpService = redisOtpService;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
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


//    @Override
//    public LoginResponseDTO loginUsers(
//            LoginRequestDTO loginRequestDTO,
//            HttpServletRequest request
//    ) {
//
//        // STEP 1 → FIND USER
//        Users user = userRepository
//                .findByEmail(loginRequestDTO.getEmail())
//                .orElseThrow(() ->
//                        new RuntimeException("Invalid Email"));
//
//        // STEP 2 → MATCH PASSWORD
//        boolean passwordMatched =
//                passwordEncoder.matches(
//                        loginRequestDTO.getPassword(),
//                        user.getPassword()
//                );
//
//        if (!passwordMatched) {
//            throw new RuntimeException("Invalid Password");
//        }
//
//        // STEP 3 → GENERATE ACCESS TOKEN
//        String accessToken =
//                jwtTokenProvider.generateToken(
//                        user.getId(),
//                        user.getEmail(),
//                        user.getName()
//                );
//
//        // STEP 4 → GENERATE REFRESH TOKEN
////        String refreshToken =
////                jwtTokenProvider.generateRefreshToken(
////                        user.getId()
////                );
//
//        // STEP 5 → SAVE REFRESH TOKEN IN DB
//
//        String refreshToken = UUID.randomUUID().toString();
//        String userAgent = request.getHeader("User-Agent"); // Yeh User-Agent By Default hai  header se user ka browser ya device information milta hai
//
//        if(userAgent ==null){
//            userAgent = "Unknown";
//        }
//
//        RefreshToken refreshTokenEntity =
//                new RefreshToken();
//
//        refreshTokenEntity.setRefreshToken(refreshToken);
//        refreshTokenEntity.setUser(user);
//

    /// /        refreshTokenEntity.setExpiryDate(
    /// /                LocalDateTime.now().plusDays(7)
    /// /        );
//        refreshTokenEntity.setExpiryDate(
//                LocalDateTime.now()
//                        .plusSeconds(
//                                refreshTokenExpiration / 1000
//                        )
//        );
//        refreshTokenEntity.setRevoked(false);
//        refreshTokenEntity.setCreatedByIpAddress(request.getRemoteAddr());
//        refreshTokenEntity.setUserAgent(userAgent);
//        refreshTokenRepository.save(refreshTokenEntity);
//        // STEP 6 → GET ACCESS TOKEN EXPIRY
//        Date expiry =
//                jwtTokenProvider.getTokenExpiration(accessToken);
//
//        // STEP 7 → PREPARE RESPONSE
//        LoginResponseDTO response =
//                new LoginResponseDTO();
//
//        response.setAccessToken(accessToken);
//        response.setRefreshToken(refreshToken);
//        response.setUserId(user.getId());
//        response.setEmail(user.getEmail());
//        response.setFullName(user.getName());
//        response.setExpiresAt(expiry);
//        return response;
//    }
    @Override
    public OtpResponseDTO loginUsers(LoginRequestDTO loginRequestDTO, HttpServletRequest request) {

        Users user = userRepository
                .findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        boolean matched =
                passwordEncoder.matches(
                        loginRequestDTO.getPassword(),
                        user.getPassword()
                );

        if (!matched) {
            throw new RuntimeException("Invalid Password");
        }

        Long attempts = redisOtpService.incrementOtpAttempt(user.getEmail());

        if (attempts > 3) {

            throw new RuntimeException("Maximum OTP limit reached. Try again after 10 minutes");
        }

        String otp =
                String.valueOf((ThreadLocalRandom.current()
                        .nextInt(100000,
                                999999
                        )
                ));

        System.out.println(
                "Generated OTP = " + otp
        );
//        EmailOtp emailOtp = new EmailOtp();
//        emailOtp.setEmail(user.getEmail());
//        emailOtp.setOtp(otp);
//        emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
//        emailOtp.setVerified(false);
//        emailOtp.setCreatedAt(LocalDateTime.now());
//        emailRepository.save(emailOtp);
//        return new OtpResponseDTO();
        redisOtpService.saveOtp(user.getEmail(), otp);
        return new OtpResponseDTO();
    }


    @Override
    public LoginResponseAccessToken refreshTokenExpiration(RefreshTokenRequestDTO request) {

        Optional<RefreshToken> refreshTokenEntity = Optional.ofNullable(refreshTokenRepository
                .findByRefreshToken(request.getRefreshToken()
                ).orElseThrow(() -> new RuntimeException("Refresh Token Not Found")));

        if (refreshTokenEntity.get().getRevoked()) {

            throw new RuntimeException("Refresh Token Revoked");
        }

        if (refreshTokenEntity.get().getExpiryDate().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Refresh Token Expired");
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

        LoginResponseAccessToken loginResponseAccessToken = new LoginResponseAccessToken();

        loginResponseAccessToken.setAccessToken(newAccessToken);
        return loginResponseAccessToken;
    }

    @Override
    public void logout(String refreshToken) {

        RefreshToken refreshTokenLogout = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new RuntimeException("Refresh Token Not Found"));

        refreshTokenLogout.setRevoked(true);
        refreshTokenRepository.save(refreshTokenLogout);
    }
//
//    @Override
//    public void sendOtp(String email, String otp) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Your OTP Code");
//        message.setText("Your OTP code is: " + otp);
//
//        javaMailSender.send(message);
//
//    }

//    @Override
//    public LoginResponseDTO verifyOtp(VerifyOtpRequestDTO request, HttpServletRequest httpServletRequest) {
//

//        EmailOtp emailOtp = emailRepository.findTopByEmailOrderByCreatedAtDesc(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("OTP Not Found for Email"));
//
//        if(emailOtp.getExpiryTime().isBefore(LocalDateTime.now())){
//            throw new RuntimeException("OTP Expired ! Please Request a New One");
//        }
//        if(!emailOtp.getOtp().equals(request.getOtp())){
//            throw new RuntimeException("Invalid OTP");
//        }
//
//        Users user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User Not Found"));
//
//            String accessToken = jwtTokenProvider.generateToken(
//                    user.getId(),
//                    user.getEmail(),
//                    user.getName()
//            );
//
//                String refreshToken = UUID.randomUUID().toString();
//                String userAgent = httpServletRequest.getHeader("User-Agent"); // Yeh User-Agent By Default hai  header se user ka browser ya device information milta hai
//
//        RefreshToken refreshTokenEntity = new RefreshToken();
//                refreshTokenEntity.setRefreshToken(refreshToken);
//                refreshTokenEntity.setUser(user);
//                refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
//                refreshTokenEntity.setCreatedByIpAddress(httpServletRequest.getRemoteAddr());
//                refreshTokenEntity.setUserAgent(userAgent);
//                refreshTokenEntity.setCreatedAt(LocalDateTime.now());
//                refreshTokenRepository.save(refreshTokenEntity);
//
//                LoginResponseDTO responseDTO = new LoginResponseDTO();
//                responseDTO.setAccessToken(accessToken);
//                responseDTO.setRefreshToken(refreshToken);
//                responseDTO.setExpiresAt(jwtTokenProvider.getTokenExpiration(accessToken));
//                responseDTO.setUserId(user.getId());
//                responseDTO.setEmail(user.getEmail());
//                responseDTO.setFullName(user.getName());
//                emailOtp.setVerified(true);
//               // emailRepository.save(emailOtp);
//                String storedOtp = redisOtpService.getOtp(request.getEmail());
//
//                if(storedOtp != null && storedOtp.equals(request.getOtp())){
//                    throw  new RuntimeException("OTP Expired");
//                }
//
//                    if(!storedOtp.equals(request.getOtp())){
//
//                        throw new RuntimeException(
//                                "Invalid OTP"
//                        );
//                    }
//
//                    redisOtpService.deleteOtp(request.getEmail());
//                return responseDTO;
//
//}


    @Override
    public LoginResponseDTO verifyOtp(VerifyOtpRequestDTO request, HttpServletRequest httpServletRequest
    ) {

        // STEP 1 -> GET OTP FROM REDIS
        String storedOtp =
                redisOtpService.getOtp(
                        request.getEmail()
                );

        System.err.println(storedOtp + "Otp Stored------");

        System.out.println(
                "Request OTP = " + request.getOtp()
        );
        // STEP 2 -> OTP EXPIRED
        if (storedOtp == null) {

            throw new RuntimeException(
                    "OTP Expired ! Please Request a New One"
            );
        }

        // STEP 3 -> OTP MISMATCH
        if (!storedOtp.equals(request.getOtp())) {

            throw new RuntimeException(
                    "Invalid OTP"
            );
        }

        // STEP 4 -> DELETE OTP (ONE TIME USE)
        //redisOtpService.deleteOtp(request.getEmail());

        // STEP 5 -> FIND USER
        Users user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException("User Not Found")
                );


        // STEP 6 -> GENERATE ACCESS TOKEN
        String accessToken =
                jwtTokenProvider.generateToken(
                        user.getId(),
                        user.getEmail(),
                        user.getName()
                );

        // STEP 7 -> GENERATE REFRESH TOKEN
        String refreshToken =
                UUID.randomUUID().toString();

        String userAgent =
                httpServletRequest.getHeader(
                        "User-Agent"
                );

        // STEP 8 -> SAVE REFRESH TOKEN
        RefreshToken refreshTokenEntity = new RefreshToken();

        refreshTokenEntity.setRefreshToken(refreshToken);
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenEntity.setCreatedAt(LocalDateTime.now());
        refreshTokenEntity.setCreatedByIpAddress(httpServletRequest.getRemoteAddr());
        refreshTokenEntity.setUserAgent(userAgent);
        refreshTokenRepository.save(refreshTokenEntity);

        // STEP 9 -> RESPONSE
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        responseDTO.setAccessToken(accessToken);

        responseDTO.setRefreshToken(refreshToken);
        responseDTO.setExpiresAt(jwtTokenProvider.getTokenExpiration(accessToken));
        responseDTO.setUserId(user.getId());

        responseDTO.setEmail(user.getEmail());

        responseDTO.setFullName(user.getName());
        return responseDTO;
    }


    @Override
    public OtpResponseDTO resendOtp(
            ResendOtpRequestDTO request
    ) {

        Users user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        Long attempts =
                redisOtpService.incrementOtpAttempt(
                        request.getEmail()
                );

        System.out.println(
                "Resend Attempt Count = " + attempts
        );


        if (attempts > 3) {

            throw new RuntimeException(
                    "OTP resend limit reached. Try again after 10 minutes."
            );
        }

        // Generate New OTP
        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        // Save New OTP In Redis
        redisOtpService.saveOtp(
                user.getEmail(),
                otp
        );

        // Debug Only
        System.out.println(
                "Resend OTP = " + otp
        );

        OtpResponseDTO response =
                new OtpResponseDTO();

        response.setEmail(
                user.getEmail()
        );

        return response;
    }

    @Override
    public Users getUser(Long userId) throws Exception {

        String key = "user:" + userId;


        String cachedValue = redisTemplate.opsForValue().get(key);

        if(cachedValue!=null){

            System.out.println(cachedValue +"REDIS HIT");

            return objectMapper.readValue(
                    cachedValue,
                    Users.class
            );
        }
        System.out.println("Redis Miss");

        Users user =
                userRepository.findById(userId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User Not Found"
                                )
                        );

        redisTemplate.opsForValue().set(
                key,
                objectMapper.writeValueAsString(user),
                Duration.ofMinutes(5)
        );
        return user;
    }


}

