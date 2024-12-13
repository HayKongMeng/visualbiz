package com.example.springfinalproject.controller.order;


import com.example.springfinalproject.model.Entity.Order;
import com.example.springfinalproject.model.request.OrderBookHistoryRequest;
import com.example.springfinalproject.model.request.OrderHistoryRequest;
import com.example.springfinalproject.model.request.OrderProductRequest;
import com.example.springfinalproject.model.request.ProductWithQtyRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.repository.OrderRepository;
import com.example.springfinalproject.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/order")
@CrossOrigin
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


    //seller
    //Get all order
    @GetMapping
    @Operation(summary = "Get All order where status is waiting(seller)")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        ApiResponse<List<Order>> response = ApiResponse.<List<Order>>builder()
                .message("Get all order successfully")
                .payload(orderService.getAllOrders())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //seller
    //Get all order history
    @GetMapping("/seller/history")
    @Operation(summary = "Get all history(Seller)")
    public ResponseEntity<ApiResponse<List<OrderHistoryRequest>>> getAllOrderHistory() {
        ApiResponse<List<OrderHistoryRequest>> response = ApiResponse.<List<OrderHistoryRequest>>builder()
                .message("Get all order history successfully")
                .payload(orderService.getAllOrdersHistory())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //customer
    //Get all order action customer
    @GetMapping("/action")
    @Operation(summary = "Get all history in action(Customer)")
    public ResponseEntity<ApiResponse<List<OrderHistoryRequest>>> getAllOrderHistoryCustomer(){
        ApiResponse<List<OrderHistoryRequest>> response = ApiResponse.<List<OrderHistoryRequest>>builder()
                .message("Get all order history successfully")
                .payload(orderService.getAllOrderHistoryCustomer())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //customer
    //Get all order history customer
    @GetMapping("/customer/history")
    @Operation(summary = "Get all history order customer page(Customer)")
    public ResponseEntity<ApiResponse<List<OrderBookHistoryRequest>>> getAllOrderHistoryCustomerPage(){
        ApiResponse<List<OrderBookHistoryRequest>> response = ApiResponse.<List<OrderBookHistoryRequest>>builder()
                .message("Get all order and book history successfully")
                .payload(orderService.getAllOrderAndBookHistoryCustomer())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //order view by id
    //Get order by id
    @GetMapping("/{id}")
    @Operation(summary = "Get Order by id(seller and customer)")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable @Positive Integer id) {
        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .message("Get order by id successfully")
                .payload(orderService.getOrderById(id))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
//        List<MappingHelper<Integer, ProductWithQtyRequest>> helperList = orderRepository.findOrderById(id);
//        Map<Integer, ProductWithQtyRequest> requestMap = MappingHelper.toMap(helperList);
        return ResponseEntity.ok(response);
    }



    //seller deny order
    @PutMapping("/order/deny/{id}")
    @Operation(summary = "Seller deny order(Seller)")
    public ResponseEntity<ApiResponse<Order>> denyOrderById(@PathVariable @Positive Integer id){
        Integer orderId = orderService.denyOrderById(id);
        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .message("Accept order successfully")
                .payload(orderService.getOrderById(orderId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //seller delivery order
    @PutMapping("/order/delivery/{id}")
    @Operation(summary = "Seller Accept order(seller)")
    public ResponseEntity<ApiResponse<Order>> deliveryOrderById(@PathVariable @Positive Integer id){
        Integer orderId = orderService.deliveryOrderById(id);

        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .message("Accept order successfully")
                .payload(orderService.getOrderById(orderId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //post order product
    @PostMapping
    @Operation(summary = "Post Order Product(Customer)")
    public ResponseEntity<ApiResponse<Order>> postOrder(@RequestBody @Valid OrderProductRequest orderProductRequest) {
        ApiResponse<Order> response = null;
        Integer createId = orderService.postOrderProduct(orderProductRequest);
        if (createId != null){
            response = ApiResponse.<Order>builder()
                    .message("Order Create Successfully")
                    .payload(orderService.getOrderById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }


    //Cancel ordering
    @PutMapping("/cancel/{id}")
    @Operation(summary = "Cancel order(customer)")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(@PathVariable @Positive Integer id) {
        Integer orderId = orderService.cancelOrderById(id);
        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .message("Accept order successfully")
                .payload(orderService.getOrderById(orderId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
}
