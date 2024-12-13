package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServiceBookRequest {
    @JsonIgnore
    private Integer user;
    @JsonIgnore
    private Integer status;
    @Min(value = 1,message = "Shop id must be bigger than 0")
    private Integer shop;
    @JsonIgnore
    private Integer bookId;
    @Valid
    @Size(min = 1, message = "There must be at least one product ID")
    private List<@Min(value = 1, message = "Service Id must be greater than 0") Integer> serviceId;
    private List<LocalDateTime> startDate;
    private List<LocalDateTime> endDate;
    @JsonIgnore
    private String notificationMessage;
}
