package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderBookHistoryRequest {
    private String username;
    private String email;
    private String productImg;
    private Integer orderId;
    private LocalDateTime orderDate;
    private String status;
    private Integer shopTypeId;
    private Float totalAmount;
}
