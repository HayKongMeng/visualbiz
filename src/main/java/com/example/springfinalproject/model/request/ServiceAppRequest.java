package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceAppRequest {
    @NotNull(message = "Service name must not be null")
    @NotBlank(message = "Service name must not be blank")
    private String serviceName;
    @NotNull(message = "Service's price is required")
    @Min(value = 1, message = "Unit price must be at least 1")
    @Digits(integer = 10, fraction = 2, message = "Unit price must be a valid number")
    private Double servicePrice;
    private String serviceDescription;
    private String serviceImg;
    @Positive
    @Min(value = 1,message = "Category must greater than 0")
    private Integer categoryId;
    private boolean isActive;
}
