package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceApp {
    private Integer serviceId;
    private String serviceName;
    private Double servicePrice;
    private String serviceDescription;
    private String serviceImage;
    private Category category;
    private boolean serviceStatus;
}
