package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderProductRequest {
    @NotNull(message = "Order Address must not be null")
    @NotBlank(message = "Order Address must not be blank")
    private String orderAddress;
    @JsonIgnore
    private Integer user;
    @JsonIgnore
    private Integer status;
    @Min(value = 1,message = "Shop id must be bigger than 0")
    private Integer shop;
    @JsonIgnore
    private Integer orderId;
    @Valid
    @Size(min = 1, message = "There must be at least one product ID")
    private List<@Min(value = 1, message = "Product Id must be greater than 0") Integer> productId;
    @JsonIgnore
    private LocalDateTime date;
    @JsonIgnore
    private String notificationMessage;
    private List<@Min(value = 1, message = "Quantity must be greater than 0") Integer> qty;

}
