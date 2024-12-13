package com.example.springfinalproject.model.Entity;

import com.example.springfinalproject.model.request.ServiceListRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    private Integer bookId;
    private List<ServiceListRequest> serviceList;
    private String status;
    private Float amount;



}
