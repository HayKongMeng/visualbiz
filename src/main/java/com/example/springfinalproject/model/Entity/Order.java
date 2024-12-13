package com.example.springfinalproject.model.Entity;

import com.example.springfinalproject.model.request.ProductWithQtyRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order  {
    private Integer orderId;
    private String orderAddress;
    private String user;
    private String profileImg;
    private String status;
    private List<ProductWithQtyRequest> productList;
    private String shopName;
    private Float TotalAmount;
}
