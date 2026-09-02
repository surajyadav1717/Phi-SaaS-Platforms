package com.dashboard.saas.service;


import com.dashboard.saas.configuration.WebSocketAuthInterceptor;
import com.dashboard.saas.dtos.NotificationListResponseDTO;
import com.dashboard.saas.dtos.NotificationResponseDTO;
import com.dashboard.saas.entities.Notification;
import com.dashboard.saas.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    private final SimpMessagingTemplate messagingTemplate;

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(WebSocketAuthInterceptor webSocketAuthInterceptor, SimpMessagingTemplate messagingTemplate, NotificationRepository notificationRepository) {
        this.webSocketAuthInterceptor = webSocketAuthInterceptor;
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }


    @Override
    public Notification createNotification(Long userId, String message, String title) {

        Notification savedNotification = new Notification();

        savedNotification.setUserId(userId);
        savedNotification.setMessage(message);
        savedNotification.setTitle(title);
        savedNotification.setRead(false);
        savedNotification.setCreatedAt(LocalDateTime.now());

        Notification save = notificationRepository.save(savedNotification);

        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                save
        );
        return save;
    }


    @Override
    public List<NotificationResponseDTO> getMyNotifications(Long userId) {


        List<Notification> notifications =
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(userId);

        List<NotificationResponseDTO> responseList =
                new ArrayList<>();

        for (Notification notification : notifications) {

            NotificationResponseDTO responseDTO =
                    new NotificationResponseDTO();

            responseDTO.setId(notification.getId());
            responseDTO.setMessage(notification.getMessage());
            responseDTO.setTitle(notification.getTitle());
             responseDTO.setRead(responseDTO.isRead());
            responseDTO.setCreatedAt(notification.getCreatedAt());

            responseList.add(responseDTO);
        }
        return responseList;
    }

    @Transactional
    public void markAsRead(
            Long notificationId,
            Long userId) {

        int updated =
                notificationRepository.markAsRead(
                        notificationId,
                        userId
                );

        if (updated == 0) {
            throw new RuntimeException(
                    "Notification not found"
            );
        }
    }

    @Override
    public NotificationListResponseDTO getUnreadNotifications(Long userId) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);

        List<NotificationResponseDTO> responseList =
                new ArrayList<>();

        for (Notification notification : notifications) {

            NotificationResponseDTO dto = new NotificationResponseDTO();

            dto.setId(notification.getId());
            dto.setMessage(notification.getMessage());
            dto.setTitle(notification.getTitle());
            dto.setRead(notification.getRead());
            dto.setCreatedAt(notification.getCreatedAt());

            responseList.add(dto);


        }
        NotificationListResponseDTO responseDTO = new NotificationListResponseDTO();
        responseDTO.setCount(responseList.size());
        responseDTO.setNotifications(responseList);
        return responseDTO;
    }
}

