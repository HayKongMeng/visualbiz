package com.example.springfinalproject.model.request;

import com.example.springfinalproject.model.Entity.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotNull(message = "Product's name is required")
    @NotBlank(message = "Product's name is required")
    private String productName;
    @NotNull(message = "Unit price is required")
    @Min(value = 1, message = "Unit price must be at least 1")
    @Digits(integer = 10, fraction = 2, message = "Unit price must be a valid number")
    private Double unitPrice;
    private String productDescription;
    @NotNull(message = "product qty is required")
    @Min(value = 1,message = "Product qty must be at least 1")
    private Integer productQty;
    private String productImg;
    @NotNull
    private LocalDateTime expireDate;
    @NotNull(message = "Barcode is required")
    private String barCode;
    @NotNull(message = "Category ID is required")
    @Min(value = 1)
    private Integer categoryId;
}
