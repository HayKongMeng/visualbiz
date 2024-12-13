package com.example.springfinalproject.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImportProductRequest {
    @NotNull(message = "Product is required")
    @Min(value = 1, message = "QTY should be greater than 0 ")
    private Integer productQty;
    @NotNull(message = "Import date is required")
    private LocalDateTime importDate;
}
