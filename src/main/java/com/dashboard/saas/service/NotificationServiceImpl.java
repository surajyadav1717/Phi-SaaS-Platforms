package com.dashboard.saas.service;


import com.dashboard.saas.configuration.WebSocketAuthInterceptor;
import com.dashboard.saas.entities.Notification;
import com.dashboard.saas.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;


    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(WebSocketAuthInterceptor webSocketAuthInterceptor, NotificationRepository notificationRepository) {
        this.webSocketAuthInterceptor = webSocketAuthInterceptor;
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification createNotification(Long userId, String message, String title){

        Notification savedNotification =  new Notification();

        savedNotification.setUserId(userId);
        savedNotification.setMessage(message);
        savedNotification.setTitle(title);
        savedNotification.setCreatedAt(LocalDateTime.now());

        Notification save = notificationRepository.save(savedNotification);
        return save;
    }
}
