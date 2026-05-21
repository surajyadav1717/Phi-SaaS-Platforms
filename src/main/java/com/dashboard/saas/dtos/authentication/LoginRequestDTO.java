package com.dashboard.saas.dtos.authentication;

public class LoginRequestDTO {

    private String email;

    private String password;

    public String email() {
        return email;
    }

    public LoginRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String password() {
        return password;
    }

    public LoginRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
