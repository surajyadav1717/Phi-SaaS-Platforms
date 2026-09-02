package com.dashboard.saas.springevents;

import com.dashboard.saas.service.NotificationService;
import com.dashboard.saas.service.emails.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginNotificationListener {

    private final NotificationService notificationService;

    private final EmailService emailService;

    public LoginNotificationListener(NotificationService notificationService, EmailService emailService) {
        this.notificationService = notificationService;
        this.emailService = emailService;
    }


    @EventListener
    public void handle(UserLoggedInEvent event) {

        notificationService.createNotification(
                event.getUserId(),
                event.getEmail(),
                event.getIpAddress()
        );

        emailService.sendEmail(
                event.getEmail(),
                "New Login Detected",
                "A New Login Was Detected On Your Account From IP Address: " + event.getIpAddress(),
                event.getUserId()
        );
    }
}


