package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.Promotion;
import com.example.springfinalproject.model.dto.PromotionDto;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.PromotionRequest;
import com.example.springfinalproject.model.response.PromotionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionService {

    List<PromotionTbWithId> getAllPromotion(Integer page, Integer size);

    List<PromotionResponse> createPromotionForProduct(PromotionRequest promotionRequest);

    PromotionDto getPromotionById(Integer promotionId);

    Integer updatePromotionById(PromotionRequest promotionRequest, Integer promotionId);

    List<PromotionTbWithId> getPromotionShopId(Integer shopId, Integer page, Integer size);

    //Get oldest promotion for seller
    List<PromotionTbWithId> getOldesPromotion();
    //Get this week promotion for seller

    List<PromotionTbWithId> getThisWeekPromotion();

    PromotionTbWithId deletePromotionWithId(Integer promotionId);

    //get current shop
    List<PromotionTbWithId> getPromotionCurrentShop(Integer page, Integer size);
}
