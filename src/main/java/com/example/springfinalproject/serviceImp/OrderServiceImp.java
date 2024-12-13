package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Order;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.*;
import com.example.springfinalproject.repository.*;
import com.example.springfinalproject.services.EmailService;
import com.example.springfinalproject.services.OrderService;
import com.example.springfinalproject.utils.GetCurrentUser;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final StatusRepository statusRepository;
    private final EmailService emailService;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Order> getAllOrders() {
        LocalDateTime now = LocalDateTime.now();
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return orderRepository.findAllOrder(now,shopId);
    }

    @Override
    public Order getOrderById(Integer id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null){
            throw new NotFoundException("Order " + id +" not exist");
        }
        return orderRepository.findOrderById(id);
    }


    @Override
    public Integer postOrderProduct(OrderProductRequest orderProductRequest) {
        LocalDateTime now = LocalDateTime.now();
        Integer userId = GetCurrentUser.currentId();
        orderProductRequest.setUser(userId);
        List<Integer> productId = orderProductRequest.getProductId();
        List<Integer> qty = orderProductRequest.getQty();


        //get shop id
        Shop shopId = shopRepository.findShopById(orderProductRequest.getShop());
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }


        Integer orderId = orderRepository.insertOrder(orderProductRequest, now);
        orderProductRequest.setNotificationMessage("User " + orderRepository.getUsernameByUserId(userId) + " has been order " + orderId + " on " + now);
        Integer notification = orderRepository.insertNotification(now, orderProductRequest.getNotificationMessage(), orderProductRequest.getShop(), userId);


        orderRepository.insertOrderNotification(orderId, notification);

        //check when input upto 2 product must enter 2 qty too
        if (productId.size() != qty.size()) {
            throw new IllegalArgumentException("Product IDs and quantities must have the same length.");
        }
        //        =============================================


        //check if product have or not
        List<Integer> checkProductId = orderRepository.getProductByShopId(shopId);
        for (Integer proId : productId) {
            if (!checkProductId.contains(proId)) {
                throw new NotFoundException("Product id: " + proId + " does not exist or does not belong to the shop.");
            }
        }
        //        =============================================


        // Insert into table
        for (int i = 0; i < productId.size(); i++) {
            Integer proId = productId.get(i);
            Integer quantity = qty.get(i);

            orderRepository.insertOrderProduct(orderId, proId, quantity);
        }
        //        =============================================


        return orderId;
    }

    @Override
    public Integer denyOrderById(Integer id) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer foundOrderId = orderRepository.findOrderByShopId(id, shopId);
        if (foundOrderId == null) {
            throw new NotFoundException("Shop does not have this order");
        }
        return orderRepository.updateOrderStatusDeny(id, shopId);
    }

    @Override
    public Integer deliveryOrderById(Integer orderId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer foundOrderId = orderRepository.findOrderByShopId(orderId, shopId);
        if (foundOrderId == null) {
            throw new NotFoundException("Shop does not have this order");
        }
        InvoiceRequest invoiceRequest = orderRepository.findOrderByOrderId(orderId);
        System.out.println(invoiceRequest);
        System.out.println(invoiceRequest.getUsername()+ " " +invoiceRequest.getOrderDate()+ " " + invoiceRequest.getOrderId()+ " " +invoiceRequest.getShopName()+ " " +invoiceRequest.getShopAddress()+ " " +invoiceRequest.getEmail());

        List<Integer> productQty = orderRepository.getProductTableByOrderId(orderId);
        List<Integer> orderQty = orderRepository.getQtyByOrderId(orderId);
        System.out.println("==========productQty" + productQty);
        System.out.println("==========orderQty" + orderQty);

        //check product qty
        Order order = orderRepository.findOrderById(orderId);
        List<ProductWithQtyRequest> products = order.getProductList();


        if (orderRepository.getStatusIdByOrderId(orderId).equals(1)){
            return orderRepository.updateOrderStatus(orderId, shopId);
        }
        else if (orderRepository.getStatusIdByOrderId(orderId).equals(2)) {
            return orderRepository.updateOrderStatusDelivery(orderId, shopId);
        } else if (orderRepository.getStatusIdByOrderId(orderId).equals(4)) {
            try {
                emailService.sendInvoice(invoiceRequest.getProfileImg(),invoiceRequest.getUsername(), invoiceRequest.getOrderDate(), invoiceRequest.getOrderId(), invoiceRequest.getShopName(), invoiceRequest.getShopAddress(), invoiceRequest.getEmail(), products);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return orderRepository.updateOrderStatusDone(orderId, shopId);
        }
        return orderId;
    }

    @Override
    public List<OrderHistoryRequest> getAllOrdersHistory() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<OrderHistoryRequest> orderHistoryRequests = orderRepository.findAllOrderHistory(shopId);
        if (orderHistoryRequests.isEmpty()){
            throw new NotFoundException("No order history found for shop " + shopId);
        }
        return orderHistoryRequests;
    }

    @Override
    public List<OrderHistoryRequest> getAllOrderHistoryCustomer() {
        Integer userId = GetCurrentUser.currentId();
        List<OrderHistoryRequest> orderHistoryRequests = orderRepository.findAllOrderHistoryCustomer(userId);
        if (orderHistoryRequests.isEmpty()){
            throw new NotFoundException("No order history found for user " + userId);
        }
        return orderHistoryRequests;
    }

    @Override
    public List<OrderBookHistoryRequest> getAllOrderAndBookHistoryCustomer() {
        Integer userId = GetCurrentUser.currentId();
        return orderRepository.findAllOrderAndBookHistory(userId);
    }

    @Override
    public Integer cancelOrderById(Integer orderId) {
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Order does not exist.");
        }

        return orderRepository.cancelOrder(orderId);
    }


}
