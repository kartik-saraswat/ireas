package com.example.rishikesh.ireas.rest.model;

import java.util.List;

public class NotificationList {

    private Long userId;

    private List<Notification> notificationList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public String toString() {
        return "NotificationList{" +
                "userId=" + userId +
                ", notificationList=" + notificationList +
                '}';
    }
}