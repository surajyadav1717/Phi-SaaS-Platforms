package com.dashboard.saas.controllers.redis;


import com.dashboard.saas.dtos.NotificationResponseDTO;
import com.dashboard.saas.entities.Notification;
import com.dashboard.saas.repositories.NotificationRepository;
import com.dashboard.saas.security.SecurityContextHelper;
import com.dashboard.saas.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final SecurityContextHelper securityContextHelper;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public NotificationController(SecurityContextHelper securityContextHelper, NotificationRepository notificationRepository, NotificationService notificationService) {
        this.securityContextHelper = securityContextHelper;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }


    @GetMapping("/all-notifications")
    public List<NotificationResponseDTO> getMyNotifications() {

        Long userId= SecurityContextHelper.getCurrentUserId();

        return notificationService
                .getMyNotifications(userId);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id) {

        Long userId =
                securityContextHelper.getCurrentUserId();

        notificationService.markAsRead(
                id,
                userId
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-unread")
    List<NotificationResponseDTO> getUnreadNotifications(Long userId)  {

        userId = securityContextHelper.getCurrentUserId();


        return notificationService
                .getUnreadNotifications(userId);
    }

}
