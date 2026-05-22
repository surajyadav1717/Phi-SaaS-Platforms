package com.dashboard.saas.entities.principle;

public class UserPrincipal {

        private Long userId;

        private String email;

        private String fullName;

    public UserPrincipal() {
    }

    public UserPrincipal(Long userId, String email, String fullName) {
            this.userId = userId;
            this.email = email;
            this.fullName = fullName;
        }

    public Long userId() {
        return userId;
    }

    public UserPrincipal setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String email() {
        return email;
    }

    public UserPrincipal setEmail(String email) {
        this.email = email;
        return this;
    }

    public String fullName() {
        return fullName;
    }

    public UserPrincipal setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
