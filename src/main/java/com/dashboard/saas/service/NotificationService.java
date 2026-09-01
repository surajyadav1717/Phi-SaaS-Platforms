package com.dashboard.saas.service;


import com.dashboard.saas.dtos.NotificationResponseDTO;
import com.dashboard.saas.entities.Notification;

import java.util.List;

public interface NotificationService {


    public Notification createNotification(Long userId, String message, String title);

    public List<NotificationResponseDTO> getMyNotifications(Long userId);

    public void markAsRead(
            Long notificationId,
            Long userId);

    public List<NotificationResponseDTO> getUnreadNotifications(Long userId);


}
