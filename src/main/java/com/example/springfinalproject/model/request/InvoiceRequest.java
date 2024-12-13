package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InvoiceRequest {
    private String username;
    private String profileImg;
    private LocalDateTime orderDate;
    private Integer orderId;
    private String shopName;
    private String shopAddress;
    private String email;
}
