package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ShopService {

    List<Shop> getAllShop(Integer page, Integer size);

    Shop getShopById(Integer id);

    Integer createNewShop( String lat, String longitude,ShopCreateRequest shopCreateRequest);

    Integer updateShop(ShopUpdateRequest shopUpdateRequest);

    boolean deleteShop();

    List<Shop> getShopByName(String name);

    Integer updateShopDescription(ShopUpdateDescriptionRequest shopUpdateDescriptionRequest);

    boolean activateShop();

    Integer postShopDayOff(ShopDayOffRequest shopDayOffRequest);

    Integer uploadShopImg(ShopProfileRequest shopProfileRequest);

    Integer updateLocation(ShopLocationRequest shopLocationRequest);

    Shop getShopCurrentUser();

    Integer uploadShopCoverImg(ShopCoverImageRequest shopCoverImageRequest);

    ShopTotalRequest getTotalInDashboard();

    List<ShopFeedbackRequest> getFeedbackSeller();

    List<Order> getAllOrders();


    List<Shop> findNearestLocation(String lat, String longitude);
    List<PopularShop> getPopularShopByRate();

    List<PopularShop> getPopularServiceShopByRate();

    PopularShop getRatingProductShopByShopId(Integer shopId);

    PopularShop getRatingServiceShopByShopId(Integer shopId);

    OverviewServiceProvider getOverviewServiceProvider();
}
