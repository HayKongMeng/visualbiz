package com.example.springfinalproject.model.request;

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
@Data
@Builder
public class ServicePromotionRequest {
    @NotNull(message = "promotion title is required.")
    private String promotionTitle;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0", inclusive = false)
    private Double percentage;
    @NotNull
    @NotBlank(message = "Product description is required.")
    private String promotionDescription;
    @NotNull(message = "Start date is required.")
    private LocalDateTime startDate;
    @NotNull(message = "End date is required.")
    private LocalDateTime endDate;
    @NotNull(message = "Please fill image for service")
    private String serviceImg;
    @NotNull
    private List<Integer> serviceId;
}
