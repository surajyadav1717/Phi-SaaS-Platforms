package com.dashboard.saas.service.authentication;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

@Service
public class RedisOtpServiceImpl implements RedisOtpService{

    private static final String EMAIL_OTP_PREFIX = "email:otp:";
    private static final String OTP_COOLDOWN_PREFIX = "otp:cooldown:";

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
    public Long incrementRequestCount(String key) {


        Long count = redisTemplate.opsForValue()
                .increment(key);


        if(count !=null  && count==1){


            redisTemplate.expire(key,Duration.ofMinutes(1));
        }
        return count;
    }


















//    //resend otp
//    public boolean isResendCooldownActive(String email) {
//
//        return Boolean.TRUE.equals(
//                redisTemplate.hasKey(
//                        OTP_COOLDOWN_PREFIX + email
//                )
//        );
//    }
//
//    public void startResendCooldown(
//            String email
//    ) {
//
//        redisTemplate.opsForValue().set(
//                OTP_COOLDOWN_PREFIX + email,
//                "ACTIVE",
//                Duration.ofSeconds(10)
//        );
//        }
}
