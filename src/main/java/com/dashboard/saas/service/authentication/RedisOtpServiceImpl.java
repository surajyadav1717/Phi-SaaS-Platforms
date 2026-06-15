package com.dashboard.saas.service.authentication;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RedisOtpServiceImpl implements RedisOtpService{

    private static final String EMAIL_OTP_PREFIX = "email:otp:";


    private final RedisTemplate<String,String> redisTemplate;

    public RedisOtpServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void saveOtp(String email, String otp) {
        redisTemplate.opsForValue().set(EMAIL_OTP_PREFIX+email,
                                            otp, Duration.ofMinutes(5));
    }

    @Override
    public String getOtp(String email) {
        return  redisTemplate.opsForValue().get(EMAIL_OTP_PREFIX +email);
    }

    @Override
    public void deleteOtp(String email) {

        redisTemplate.delete(EMAIL_OTP_PREFIX+email);
    }
}
