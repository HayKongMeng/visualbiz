package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookHistoryRequest {
    private String username;
    private String email;
    private String serviceImage;
    private Integer bookId;
    private LocalDateTime startDate;
    private String status;
    private Integer shopTypeId;
    private Float totalAmount;
}
