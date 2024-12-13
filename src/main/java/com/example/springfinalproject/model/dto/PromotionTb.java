package com.example.springfinalproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionTb {
    private double percentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer shopId;
    private String promotiontitle;
    private String promotionDescription;
    private String promotionImage;
    private boolean isExpired;
    private double discountPrice;
}
