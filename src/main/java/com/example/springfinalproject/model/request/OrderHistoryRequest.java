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
public class OrderHistoryRequest {
    private String username;
    private String email;
    private String image;
    private Integer orderId;
    private LocalDateTime orderDate;
    private String status;
    private Float totalAmount;
}
