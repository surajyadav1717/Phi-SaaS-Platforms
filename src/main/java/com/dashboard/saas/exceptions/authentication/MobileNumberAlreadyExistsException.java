package com.dashboard.saas.exceptions.authentication;

public class MobileNumberAlreadyExistsException  extends RuntimeException{

    public MobileNumberAlreadyExistsException(String message) {
        super(message);
    }
}
