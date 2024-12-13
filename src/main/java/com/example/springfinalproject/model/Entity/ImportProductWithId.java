package com.example.springfinalproject.model.Entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImportProductWithId {
    private Integer importId;
    @NotNull(message = "Product is required")
    @Min(value = 1, message = "QTY should be greater than 0 ")
    private Integer productQty;
    @NotNull(message = "Import date is required")
    private LocalDateTime importDate;
}
