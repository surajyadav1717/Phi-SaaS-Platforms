package com.dashboard.saas.exceptions.authentication;

public class EmailAlreadyExistsException  extends  RuntimeException{

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
