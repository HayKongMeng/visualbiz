package com.example.springfinalproject.model.request;

import jakarta.validation.constraints.*;
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
public class PromotionRequest {
    @NotNull(message = "promotion title is required.")
    private String promotionTitle;
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0", inclusive = false)
    private double percentage;
    @NotNull(message = "Start date is required.")
    private LocalDateTime startDate;
    @NotNull(message = "End date is required.")
    private LocalDateTime endDate;
    private String promotionDescription;
    @NotNull
    @NotBlank(message = "Product description is required.")
    private String promotionImage;
    @NotNull
    private List<Integer> productIds;
}
