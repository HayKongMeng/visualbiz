package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.*;
import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.*;
import com.example.springfinalproject.repository.*;
import com.example.springfinalproject.services.ShopService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopServiceImp implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<Shop> getAllShop(Integer page, Integer size) {
        return shopRepository.findAllShop( page,  size);
    }

    @Override
    public Shop getShopById(Integer id) {
        Shop shopId = shopRepository.findShopById(id);
        if (shopId == null) {
            throw new NotFoundException("Shop " + id + " not exist");
        }
        return shopRepository.findShopById(id);
    }


    //CREATE NEW
    @Override
    public Integer createNewShop( String lat, String longitude,ShopCreateRequest shopCreateRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shop = shopRepository.findShopByUserId(userId);
        if(shop!=null){
            throw new AlreadyCreateException( userId + " already have a shop");
        }
        ShopType shopType = shopTypeRepository.findShopTypeById(shopCreateRequest.getShopTypeId());
        if (shopType == null){
            throw new BadRequestException("Shop type " + shopCreateRequest.getShopTypeId() + " not valid");
        }

        shopCreateRequest.setUserId(userId);
        Integer typeId = shopCreateRequest.getShopTypeId();
        System.out.println(typeId);
        shopCreateRequest.setLat(lat);
        shopCreateRequest.setLongti(longitude);
        if(typeId==3){
            roleRepository.insertUserRole(1,userId);
            roleRepository.insertUserRole(2,userId);
        }else{
            roleRepository.insertUserRole(1,userId);
            roleRepository.insertUserRole(typeId+1, userId);
        }
        return shopRepository.insertNewShop(shopCreateRequest);
    }

    //UPDATE SHOP INFO
    @Override
    public Integer updateShop( ShopUpdateRequest shopUpdateRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopUpdateRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.editShop(shopUpdateRequest);
    }


    //delete shop
    @Override
    public boolean deleteShop() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }

        Boolean shopStatus = shopRepository.shopStatus(shopId);

        if (shopStatus == null || !shopStatus) {
            throw new NotFoundException("Shop does not exist or is inactive. Please create a new shop.");
        }
        return shopRepository.deleteShop(shopId);
    }

    @Override
    public List<Shop> getShopByName(String name) {
        List<Shop> shop = shopRepository.findShopByName(name);
        System.out.println("==============" +shop);

        if (shop.isEmpty()) {
            throw new NotFoundException("Shop name " + name + " not exist");
        }
        //if(shop)
        return shop;
    }

    @Override
    public Integer updateShopDescription(ShopUpdateDescriptionRequest shopUpdateDescriptionRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopUpdateDescriptionRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.editShopDescription(shopUpdateDescriptionRequest);
    }

    @Override
    public boolean activateShop() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist for the current user.");
        }

        Boolean shopStatus = shopRepository.shopStatus(shopId);

        if (shopStatus == null || shopStatus) {
            throw new NotFoundException("Shop does not exist or already active.");
        }
        return shopRepository.activeShop(shopId);
    }

    @Override
    public Integer postShopDayOff(ShopDayOffRequest shopDayOffRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopDayOffRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.insertShopDayOff(shopDayOffRequest);
    }

    @Override
    public Integer uploadShopImg(ShopProfileRequest shopProfileRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopProfileRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        if (!shopProfileRequest.getShopProfileImg().contains(".png") || shopProfileRequest.getShopProfileImg().contains(".jpeg") || shopProfileRequest.getShopProfileImg().contains(".jpg")){
            throw new FileUploadException("File must be contain jpg, png, jpeg");
        }
        return shopRepository.insertShopImg(shopProfileRequest);
    }

    @Override
    public Integer updateLocation(ShopLocationRequest shopLocationRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopLocationRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.editShopLocation(shopLocationRequest);
    }

    @Override
    public Shop getShopCurrentUser() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        if (userId == null) {
            throw new NotFoundException("User does not exist.");
        }
        return shopRepository.findShopByCurrentUser(userId);
    }

    @Override
    public Integer uploadShopCoverImg(ShopCoverImageRequest shopCoverImageRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        shopCoverImageRequest.setShopId(shopId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.updateShopCoverImg(shopCoverImageRequest);
    }


    @Override
    public ShopTotalRequest getTotalInDashboard() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer sale = shopRepository.findTotalSale(shopId);
        Integer product = shopRepository.findTotalProduct(shopId);
        Integer customer = shopRepository.findTotalCustomer(shopId);
        Integer customerRequest = shopRepository.findTotalCustomerRequest(shopId);
        return new ShopTotalRequest(sale, product, customer,customerRequest);
    }

    @Override
    public List<ShopFeedbackRequest> getFeedbackSeller() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<ShopFeedbackRequest> shopFeedbackRequests = shopRepository.findFeedbackSeller();
        if (shopFeedbackRequests.isEmpty()){
            throw new NotFoundException("You do not have any feedback.");
        }
        return shopFeedbackRequests;
    }

    @Override
    public List<Order> getAllOrders() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return shopRepository.findAllOrders(shopId);
    }

    @Override
    public List<PopularShop> getPopularShopByRate() {
        return shopRepository.getPopularShopByRate();
    }

    @Override
    public List<PopularShop> getPopularServiceShopByRate() {
        return shopRepository.getPopularServiceShopByRate();
    }
    @Override
    public List<Shop> findNearestLocation(String lat, String longitude) {
        String point = "POINT(" + lat + " " + longitude + ")";
        // Fetch the nearby shop locations
        List<Shop> shopDetails = shopRepository.findNearbyShops(point);
        if (shopDetails.isEmpty()){
            throw new  NotFoundException("Shop does not exist");
        }
        return shopDetails;
    }

    @Override
    public PopularShop getRatingProductShopByShopId(Integer shopId) {
        if (shopId != null) {
            System.out.println(shopId);
            System.out.println(shopRepository.getRatingProductShopByShopId(shopId));
            return shopRepository.getRatingProductShopByShopId(shopId);
        }throw new NotFoundException("Shop does not exist.");
    }

    @Override
    public PopularShop getRatingServiceShopByShopId(Integer shopId) {
        if (shopId != null) {
            System.out.println(shopId);
            System.out.println(shopRepository.getRatingServiceShopByShopId(shopId));
            return shopRepository.getRatingServiceShopByShopId(shopId);
        }throw new NotFoundException("Shop does not exist.");
    }

    @Override
    public OverviewServiceProvider getOverviewServiceProvider() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer customer = shopRepository.getCustomer(shopId);
        Integer appointment = shopRepository.getAppointment(shopId);
        Integer service = shopRepository.getService(shopId);
        Integer feedback = shopRepository.getFeedback(shopId);
        return new OverviewServiceProvider(customer, appointment, service, feedback);
    }
}
