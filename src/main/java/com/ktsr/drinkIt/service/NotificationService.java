package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.NotificationDto;
import com.ktsr.drinkIt.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(NotificationDto dto);

    Notification getNotificationById(Long id);

    List<Notification> getNotificationsByUser(Long userId);

    List<Notification> getUnreadNotifications(Long userId);

    Notification markAsRead(Long id);

    boolean existsById(Long id);

    void deleteNotification(Long id);
}
