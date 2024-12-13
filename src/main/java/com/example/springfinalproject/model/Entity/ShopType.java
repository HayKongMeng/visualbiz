package com.example.springfinalproject.model.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopType {
    private Integer shopTypeId;
    private String shopType;
}
