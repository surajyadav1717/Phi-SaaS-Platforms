package com.dashboard.saas.interceptior;

import com.dashboard.saas.exceptions.authentication.RateLimitReachedException;
import com.dashboard.saas.service.authentication.RedisOtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class RateLimitInterceptorServiceImpl implements HandlerInterceptor,RateLimitInterceptorService {

    private final RedisOtpService redisOtpService;

    private final RedisTemplate redisTemplate;

    private static final int LIMIT = 10;

    public RateLimitInterceptorServiceImpl(RedisOtpService redisOtpService, RedisTemplate redisTemplate) {
        this.redisOtpService = redisOtpService;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException {

        String ip =
                request.getRemoteAddr();

        String key =
                "rate_limit:" + ip;

        Long count =
                redisOtpService
                        .incrementRequestCount(
                                key
                        );

        System.out.println(count+"count per req");
        if(count>LIMIT){
            System.out.println(count+"count per request exc");

            throw  new RateLimitReachedException("Too Many Request");
        }

        return  true;
    }
}
