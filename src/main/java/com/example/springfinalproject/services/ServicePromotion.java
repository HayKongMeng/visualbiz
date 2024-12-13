package com.example.springfinalproject.services;


import com.example.springfinalproject.model.request.ServicePromotionRequest;
import com.example.springfinalproject.model.response.ServicePromotionResponse;
import com.example.springfinalproject.model.response.ServiceProviderHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicePromotion {
    List<ServicePromotionResponse> createPromotionForService(ServicePromotionRequest servicePromotionRequest);

    List<ServicePromotionResponse> getAllServicePromotionForServiceProvider(Integer page, Integer size);

    List<ServiceProviderHistoryResponse> getAllBookHistory(Integer page, Integer size);

}
