package com.dashboard.saas.dtos;

import java.util.List;

public class NotificationListResponseDTO {

    private long count;
    private List<NotificationResponseDTO> notifications;


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<NotificationResponseDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationResponseDTO> notifications) {
        this.notifications = notifications;
    }
}
