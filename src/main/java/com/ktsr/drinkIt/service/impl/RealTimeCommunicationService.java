package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealTimeCommunicationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(NotificationDto dto) {

        // Send to all subscribers of this user
        messagingTemplate.convertAndSend(
                "/notification/user/" + dto.getUserId(),
                dto
        );

        // Optional: send to authenticated user destination
        messagingTemplate.convertAndSendToUser(
                dto.getUserId().toString(),
                "/queue/notifications",
                dto
        );
    }
}