package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceListRequest {
    private Integer serviceId;
    private String serviceName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
