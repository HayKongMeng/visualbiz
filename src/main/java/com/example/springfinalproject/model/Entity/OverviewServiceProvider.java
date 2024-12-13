package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OverviewServiceProvider {
    private Integer customer;
    private Integer appointment;
    private Integer service;
    private Integer feedback;
}
