package com.dashboard.saas.service.authentication;

public interface RedisOtpService {

    public void saveOtp(String email, String otp);

    public String getOtp(String email);

    public void deleteOtp(String email);

    public Long incrementOtpAttempt(String email);

    public Long incrementRequestCount(String key);

    //public boolean isResendCooldownActive(String email);

   // public void startResendCooldown(String email);
}