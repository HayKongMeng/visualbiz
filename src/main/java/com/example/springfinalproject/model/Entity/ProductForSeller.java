package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductForSeller {
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Double discount;
    private String productDescription;
    private Integer productQty;
    private String productImg;
    private LocalDateTime expireDate;
    private boolean isActive;
    private String barCode;
    private Category category;
}