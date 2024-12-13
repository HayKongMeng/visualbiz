package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductWithQtyRequest implements Serializable {
    private Integer productId;
    private Double unitPrice;
    private String productImg;
    private String productName;
    private Integer qty;

}
