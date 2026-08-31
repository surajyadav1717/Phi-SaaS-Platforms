package com.dashboard.saas.service;


import com.dashboard.saas.entities.Notification;

public interface NotificationService {


    public Notification createNotification(Long userId, String message, String title);


}
