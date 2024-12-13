package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.request.NotificationRequest;
import com.example.springfinalproject.repository.NotificationRepository;
import com.example.springfinalproject.services.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepository.findAllEvent();
    }

    @Override
    public Notification findbyid(Integer id) {
        return notificationRepository.findbyid(id);
    }

    @Override
    public Integer addNewNotification(NotificationRequest notificationRequest) {

        return notificationRepository.postNewNotification(notificationRequest);
    }

    @Override
    public Integer updateNotification(NotificationRequest notificationRequest, Integer id) {
        return notificationRepository.update(notificationRequest,id);
    }

    @Override
    public boolean deleteNotification(Integer id) {
        return notificationRepository.deleteNotification(id);
    }

}
