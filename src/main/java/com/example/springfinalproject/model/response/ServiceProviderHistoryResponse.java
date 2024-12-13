package com.example.springfinalproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceProviderHistoryResponse {
     private String customerName;
     private String email;
     private String customerImage;
     private Integer bookId;
     private LocalDateTime orderDate;
     private String bookStatus;
     private double totalAmount;
}
