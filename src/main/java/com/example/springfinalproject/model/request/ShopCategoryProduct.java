package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopCategoryProduct {
    private Integer shopId;
    private Integer productId;
    private String productName;
    private String productDescription;
    private Double unitPrice;
    private Integer categoryId;
}
