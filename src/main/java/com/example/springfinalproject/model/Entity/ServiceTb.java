package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceTb {
    private String serviceId;
    private String serviceName;
    private double servicePrice;
    private String serviceDescription;
    private String serviceImg;
    private boolean isActive;
    private Integer category;
}
