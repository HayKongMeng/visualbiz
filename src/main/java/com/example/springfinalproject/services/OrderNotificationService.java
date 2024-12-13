package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.Entity.OrderNotification;
import com.example.springfinalproject.model.request.OrderNotificationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderNotificationService {
    List<OrderNotification> findAllOrderNotification();
    Notification findOrderNotificationById(Integer id);
    Integer AddNewOrderNotification(OrderNotificationRequest orderNotificationRequest);
}
