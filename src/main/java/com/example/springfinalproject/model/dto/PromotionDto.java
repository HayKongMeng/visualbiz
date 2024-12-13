package com.example.springfinalproject.model.dto;

import com.example.springfinalproject.model.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionDto{
    private Integer promotionId;
    private String promotionTitle;
    private Double percentage;
    private List<Product> productList;
    private String promotionDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isExpired;
}
