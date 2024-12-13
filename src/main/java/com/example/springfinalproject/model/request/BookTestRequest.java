package com.example.springfinalproject.model.request;

import com.example.springfinalproject.model.Entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookTestRequest {
    private Integer bookId;
    private String serviceImage;
    private String serviceName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;
    private Double servicePrice;
    private Double percentage;
}
