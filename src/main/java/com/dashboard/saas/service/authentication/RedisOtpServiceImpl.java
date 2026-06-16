package com.dashboard.saas.service.authentication;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

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

    @Override
    public Long incrementOtpAttempt(String email) {


        String key =
                "otp:attempt:" + email;

        Long count =redisTemplate.opsForValue().increment(key);

        if(count == 1){

            redisTemplate.expire(key,Duration.ofMinutes(3));
        }

         return  count;
    }



    @Override
    public Long getOtpAttempt(String email) {

        String key = "otp:attempt:"+email;

        String value = redisTemplate.opsForValue().get(key);

        return  value == null
                ? 0
                :Long.parseLong(value);

    }
}
