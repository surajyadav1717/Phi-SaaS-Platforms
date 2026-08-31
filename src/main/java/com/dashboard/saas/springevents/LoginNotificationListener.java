package com.dashboard.saas.springevents;

import com.dashboard.saas.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginNotificationListener {

    private final NotificationService notificationService;

    public LoginNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @EventListener
    public void handle(UserLoggedInEvent event) {

        notificationService.createNotification(
                event.getUserId(),
                "New Login",
                "A New Login Was Detected On Your Account."
        );
    }
}


