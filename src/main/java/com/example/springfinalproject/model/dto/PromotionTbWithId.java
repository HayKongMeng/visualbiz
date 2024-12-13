package com.example.springfinalproject.model.dto;

import com.example.springfinalproject.model.Entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionTbWithId {
    private Integer promotionId;
    private double percentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String promotiontitle;
    private String promotionDescription;
    private String promotionImage;
    private boolean isExpired;
    private double discountPrice;
    private Shop shopId;

}
