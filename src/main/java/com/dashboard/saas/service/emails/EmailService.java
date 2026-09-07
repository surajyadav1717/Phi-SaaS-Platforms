package com.dashboard.saas.service.emails;


public interface EmailService {

    public void sendEmail(
                          String to,
                          String subject,
                          String message,
                          Long userId);

}
