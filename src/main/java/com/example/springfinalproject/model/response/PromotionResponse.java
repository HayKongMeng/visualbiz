package com.example.springfinalproject.model.response;

import com.example.springfinalproject.model.Entity.Category;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PromotionResponse {
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Double discountPercent;
    private Double priceAfterDiscount;
    private String productDescription;
    private Integer productQty;
    private String productImg;
    private LocalDateTime createDate;
    private boolean isActive;
    private Category category;
}
