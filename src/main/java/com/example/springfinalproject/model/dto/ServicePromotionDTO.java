package com.example.springfinalproject.model.dto;

import com.example.springfinalproject.model.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServicePromotionDTO {
        private Integer serviceId;
        private String serviceName;
        private String serviceTitle;
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
