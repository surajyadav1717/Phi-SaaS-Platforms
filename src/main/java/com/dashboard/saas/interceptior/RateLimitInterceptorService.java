package com.dashboard.saas.interceptior;

import com.dashboard.saas.service.authentication.RedisOtpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

public interface RateLimitInterceptorService  {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);



}
