package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long UserId);

    @Modifying
    @Query("""
    UPDATE Notification n
    SET n.isRead = true
    WHERE n.id = :notificationId
    AND n.userId = :userId
""")
    int markAsRead(
            @Param("notificationId") Long notificationId,
            @Param("userId") Long userId
    );

    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
            Long userId
    );

}
