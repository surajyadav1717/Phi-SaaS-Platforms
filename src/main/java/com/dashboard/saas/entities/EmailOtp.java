package com.dashboard.saas.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name="email_otp")
@Entity
public class EmailOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String email;

    private String otp;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    private Boolean verified;

    private LocalDateTime createdAt;

    public EmailOtp() {
    }

    public EmailOtp(Long id, String email, String otp, LocalDateTime expiryTime, Boolean verified, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.expiryTime = expiryTime;
        this.verified = verified;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
