package com.dashboard.saas.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            unique = true,
            columnDefinition = "TEXT",
            name = "refresh_token")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            nullable = false)
    private Users user;

    @Column(name = "expiry_date",
            nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean revoked = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="created_by_ip_address")
    private String createdByIpAddress;

    @Column(name = "user_agent")
    private  String UserAgent;

    public RefreshToken() {
    }

    public RefreshToken(Long id, String refreshToken, Users user, LocalDateTime expiryDate, Boolean revoked, LocalDateTime createdAt ,String createdByIpAddress,String userAgent) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.createdAt = createdAt;
        this.createdByIpAddress = createdByIpAddress;
        this.UserAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedByIpAddress() {
        return createdByIpAddress;
    }

    public void setCreatedByIpAddress(String createdByIpAddress) {
        this.createdByIpAddress = createdByIpAddress;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }
}
