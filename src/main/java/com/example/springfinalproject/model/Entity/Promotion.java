package com.example.springfinalproject.model.Entity;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Promotion {
    private Integer promotionId;
    private String promotionTitle;
    private Double percentage;
    private String promotionDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isExpired;
}
