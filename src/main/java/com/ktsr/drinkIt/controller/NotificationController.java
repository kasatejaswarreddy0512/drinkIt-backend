package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.entity.Notification;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper> getUserNotifications(@PathVariable Long userId) {
        try {
            List<Notification> notifications=notificationService.getNotificationsByUser(userId);
            return APIResponse.get(ErrorCode.SUCCESS,notifications,HttpStatus.OK);
        }catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ResponseWrapper> getUnreadNotifications(@PathVariable Long userId) {
        try {
            List<Notification> notifications=notificationService.getUnreadNotifications(userId);
            return APIResponse.get(ErrorCode.SUCCESS,notifications,HttpStatus.OK);
        }catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ResponseWrapper> markAsRead(@PathVariable Long id){
        try {
            boolean exists= notificationService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.NOTIFICATION_NOT_FOUND,"Notification not found.",HttpStatus.NOT_FOUND);
            }
            Notification notification=notificationService.markAsRead(id);
            return APIResponse.get(ErrorCode.SUCCESS,notification,HttpStatus.OK);
        }catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteNotification(@PathVariable Long id){
        try {
            boolean exists= notificationService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.NOTIFICATION_NOT_FOUND,"Notification Not Found",HttpStatus.NOT_FOUND);
            }
             notificationService.deleteNotification(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Notification Deleted Successfully",HttpStatus.OK);
        }catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
