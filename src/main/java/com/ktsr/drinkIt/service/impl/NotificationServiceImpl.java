package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.NotificationDto;
import com.ktsr.drinkIt.entity.Notification;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.repository.NotificationRepository;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final RealTimeCommunicationService realTimeCommunicationService;

    @Override
    public Notification createNotification(NotificationDto dto) {
        User user= userRepository.findById(dto.getUserId())
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .title(dto.getTitle())
                .message(dto.getMessage())
                .type(dto.getType())
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        realTimeCommunicationService.sendNotification(dto);

        return savedNotification;
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Notification not found"));
    }

    @Override
    public List<Notification> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    @Override
    public boolean existsById(Long id) {
        return notificationRepository.existsById(id);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);

    }
}
