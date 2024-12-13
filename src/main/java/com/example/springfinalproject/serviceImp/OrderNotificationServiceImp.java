package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.Entity.OrderNotification;
import com.example.springfinalproject.model.request.OrderNotificationRequest;
import com.example.springfinalproject.repository.OrderNotificationRespository;
import com.example.springfinalproject.services.OrderNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderNotificationServiceImp implements OrderNotificationService {
    private final OrderNotificationRespository orderNotificationRespository;

    public OrderNotificationServiceImp(OrderNotificationRespository orderNotificationRespository) {
        this.orderNotificationRespository = orderNotificationRespository;
    }


    @Override
    public List<OrderNotification> findAllOrderNotification() {
        return orderNotificationRespository.findAllOrderNotification();
    }

    @Override
    public Notification findOrderNotificationById(Integer id) {
        List<Integer> storeId = orderNotificationRespository.findById(id);
        System.out.println(storeId);
        return null;
    }

    @Override
    public Integer AddNewOrderNotification(OrderNotificationRequest orderNotificationRequest) {
        return orderNotificationRespository.createOrderNotification(orderNotificationRequest);
    }
}
