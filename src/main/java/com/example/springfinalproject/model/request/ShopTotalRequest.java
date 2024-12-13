package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopTotalRequest {
    private Integer sale;
    private Integer product;
    private Integer customer;
    private Integer customerRequest;
}
