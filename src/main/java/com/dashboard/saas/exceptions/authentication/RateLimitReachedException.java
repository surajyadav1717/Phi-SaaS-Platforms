package com.dashboard.saas.exceptions.authentication;

public class RateLimitReachedException  extends RuntimeException{

    public RateLimitReachedException(String message) {
        super(message);
    }

}
