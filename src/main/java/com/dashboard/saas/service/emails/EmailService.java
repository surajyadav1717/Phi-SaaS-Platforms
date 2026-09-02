package com.dashboard.saas.service.emails;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public interface EmailService {

    public void sendEmail(String to,
                          String subject,
                          String message,
                          Long userId);

}
