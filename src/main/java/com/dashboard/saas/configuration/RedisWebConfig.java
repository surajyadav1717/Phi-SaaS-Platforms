package com.dashboard.saas.configuration;

import com.dashboard.saas.interceptior.RateLimitInterceptorService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RedisWebConfig implements WebMvcConfigurer {

    private final RateLimitInterceptorService rateLimitInterceptor;

    public RedisWebConfig(RateLimitInterceptorService rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(
                (HandlerInterceptor) rateLimitInterceptor
        );
    }
}
