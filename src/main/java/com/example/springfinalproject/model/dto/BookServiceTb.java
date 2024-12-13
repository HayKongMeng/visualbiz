package com.example.springfinalproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookServiceTb {
    private Integer bookServiceId;
    private Integer bookId;
    private Integer serviceId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
