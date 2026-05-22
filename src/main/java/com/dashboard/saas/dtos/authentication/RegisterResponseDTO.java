package com.dashboard.saas.dtos.authentication;

public class RegisterResponseDTO {

    private Long userId;

    private String name;

    private String email;

    private Long mobileNumber;

    public RegisterResponseDTO() {
    }

    public RegisterResponseDTO(Long userId,
                               String name,
                               String email,
                               Long mobileNumber) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    // GETTERS

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    // SETTERS

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
