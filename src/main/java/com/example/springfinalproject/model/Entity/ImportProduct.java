package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImportProduct{
    private Product productId;
    private Integer importId;
    private Integer quantity;
    private Timestamp importDate;
}
