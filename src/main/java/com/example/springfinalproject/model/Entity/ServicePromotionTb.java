package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServicePromotionTb {
    private Integer serviceId;
    private String serviceName;
    private Float servicePrice;
    private String serviceDescription;
    private String serviceImage;
    private boolean serviceStatus;
    private Category category;
}
