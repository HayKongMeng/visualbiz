package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Order;
import com.example.springfinalproject.model.request.OrderBookHistoryRequest;
import com.example.springfinalproject.model.request.OrderHistoryRequest;
import com.example.springfinalproject.model.request.OrderProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Integer id);

    Integer postOrderProduct(OrderProductRequest orderProductRequest);



    Integer denyOrderById(Integer id);

    Integer deliveryOrderById(Integer id);

    List<OrderHistoryRequest> getAllOrdersHistory();

    List<OrderHistoryRequest> getAllOrderHistoryCustomer();

    List<OrderBookHistoryRequest> getAllOrderAndBookHistoryCustomer();

    Integer cancelOrderById(Integer orderId);
}
