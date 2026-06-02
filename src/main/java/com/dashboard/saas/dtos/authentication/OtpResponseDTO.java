package com.dashboard.saas.dtos.authentication;

public class OtpResponseDTO {

    private String message;

    public OtpResponseDTO() {
    }

    public OtpResponseDTO(String message) {
        this.message = message;
    }

    // GETTERS

    public String getMessage() {
        return message;
    }

    // SETTERS

    public void setMessage(String message) {
        this.message = message;
    }
}
