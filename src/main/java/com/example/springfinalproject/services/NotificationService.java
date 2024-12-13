package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.request.NotificationRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    List<Notification> getAllNotification();
    Notification findbyid (Integer id);
    Integer addNewNotification(NotificationRequest notificationRequest);
    Integer updateNotification(NotificationRequest notificationRequest, Integer id);
    boolean deleteNotification(Integer id);
}
