package com.example.springfinalproject.model.response;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServicePromotionResponse {
    private Integer serviceId;
    private String serviceName;
    private String promotionTitle;
    private Double unitPrice;
    private Double discountPercent;
    private Double discountUnitPrice;
    private String serviceDescription;
    private String serviceImg;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
    private boolean isActive;
    private Category category;
}
