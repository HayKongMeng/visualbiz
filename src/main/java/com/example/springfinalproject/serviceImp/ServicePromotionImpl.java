package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.dto.BookTb;
import com.example.springfinalproject.model.dto.PromotionTb;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.ServicePromotionRequest;
import com.example.springfinalproject.model.response.ServicePromotionResponse;
import com.example.springfinalproject.model.response.ServiceProviderHistoryResponse;
import com.example.springfinalproject.model.response.UserResponse;
import com.example.springfinalproject.repository.*;
import com.example.springfinalproject.services.ServicePromotion;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ServicePromotionImpl implements ServicePromotion {
    private final ServiceRepository serviceRepository;
    private final RoleRepository roleRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final PromotionRepository promotionRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    @Override
    public List<ServicePromotionResponse> createPromotionForService(ServicePromotionRequest servicePromotionRequest) {
        if(servicePromotionRequest.getStartDate().isAfter(servicePromotionRequest.getEndDate())){
            throw new BadRequestException("Invalid promotion date");
        }
        if(servicePromotionRequest.getPercentage()<0 || servicePromotionRequest.getPercentage()>100) {
            throw new BadRequestException("Invalid promotion percentage");
        }
        Integer getCurrentUserId = GetCurrentUser.currentId();
        Integer serviceProviderId = roleRepository.getRoleIdForServiceProvider(getCurrentUserId);

        if(serviceProviderId != 3){
            throw new BadRequestException( serviceProviderId + " Not a service provider ");
        }
        //Get shopId by current user
        Integer shopId = shopRepository.getShopId(getCurrentUserId);
        if(shopId==null){
            throw new NotFoundException(shopId + " Please create shop");
        }
        //Get all service in service_tb for currentUser
        List<ServiceApp> serviceAppList = serviceRepository.findAllServiceShop(shopId);
        System.out.println("serviceAppList" + serviceAppList);
//        System.out.println(" serviceAppList = serviceRepository.findAllServiceShop(shopId)" +serviceAppList );
//
        List<ServiceApp> allServiceInShop = new ArrayList<>();
        for(Integer serviceId : servicePromotionRequest.getServiceId()){
            allServiceInShop.add(serviceRepository.getAllServiceForShop(serviceId,shopId));


        }


        List<ServicePromotionResponse> servicePromotionResponses = new ArrayList<>();
        for(ServiceApp serviceApp:allServiceInShop ){
            System.out.println("serviceApp " + serviceApp.getServiceId() );
            Category category = categoryRepository.getCategoryByServiceId(serviceApp.getServiceId());
            System.out.println();
            double discountPrice = serviceApp.getServicePrice() - (serviceApp.getServicePrice()*servicePromotionRequest.getPercentage()/100);
            PromotionTb promotionTb = new PromotionTb(servicePromotionRequest.getPercentage(),servicePromotionRequest.getStartDate(),servicePromotionRequest.getEndDate(), shopId,servicePromotionRequest.getPromotionTitle(),servicePromotionRequest.getPromotionDescription(),servicePromotionRequest.getServiceImg(),false,discountPrice);
            PromotionTbWithId promotionTb1 = promotionRepository.saveToPromotionTb(promotionTb);
            if(promotionTb1==null){
                throw new BadRequestException("Input miss match!");
            }
            String serviceTitle = promotionRepository.getPromotionTitle(serviceApp.getServiceId());
            promotionRepository.saveToServicePromotionTb(serviceApp.getServiceId(),promotionTb1.getPromotionId());
            System.out.println("Category" +category);
            ServicePromotionResponse servicePromotionResponse =
                    new ServicePromotionResponse(serviceApp.getServiceId(),serviceApp.getServiceName(),serviceTitle ,serviceApp.getServicePrice(),servicePromotionRequest.getPercentage(),discountPrice,
                            servicePromotionRequest.getPromotionDescription(),servicePromotionRequest.getServiceImg(),servicePromotionRequest.getStartDate(),
                            promotionTb1.getEndDate(),true,category);

            servicePromotionResponses.add(servicePromotionResponse);
        }
        System.out.println(" servicePromotionResponses" + servicePromotionResponses);

        return servicePromotionResponses;
    }

    @Override
    public List<ServicePromotionResponse> getAllServicePromotionForServiceProvider(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopdId = shopRepository.findShopByUserId(userId);
        Integer serviceProviderId = roleRepository.getRoleIdForServiceProvider(userId);
        if(serviceProviderId == null){
           throw new BadRequestException( userId + " not a service provider ");
        }
        List<ServicePromotionResponse> servicePromotionResponseList = serviceProviderRepository.getAllServicePromotionForServiceProvider(page,size,shopdId);
        if(servicePromotionResponseList==null){
            throw new NotFoundException("Service promotion not found");
        }
        return servicePromotionResponseList;
    }

    @Override
    public List<ServiceProviderHistoryResponse> getAllBookHistory(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        Integer serviceProviderId = roleRepository.getRoleIdForServiceProvider(userId);

        if(serviceProviderId == null){
            throw new BadRequestException( userId + " not a service provider ");
        }
        List<BookTb> bookTbList = serviceProviderRepository.getAllBookForAShop(shopId);
        List<ServiceProviderHistoryResponse> serviceProviderHistoryResponseList = new ArrayList<>();
        for(BookTb bookTb : bookTbList){
            System.out.println(" bookTb " + bookTb);
        }
        for (BookTb bookTb : bookTbList) {
            UserResponse userResponse = userRepository.getUserInformation(bookTb.getUserId());
            if (userResponse == null) {
                throw new NotFoundException("User response not found");
            }
            System.out.println("userResponse = " + userResponse);

            System.out.println("bookTb.getBookId()" + bookTb.getBookId() + "shopId" +  shopId);
            LocalDateTime orderDate = serviceProviderRepository.getEndDate(bookTb.getBookId());
            System.out.println("orderDate " + orderDate);
            System.out.println("bookTb.getBookId() = " + bookTb.getBookId());
            System.out.println(" before get information from " );
            Integer serviceId = serviceProviderRepository.getServiceId(bookTb.getBookId());
            if (serviceId == null) {
                throw new NotFoundException(serviceId + " not found");
            }
            System.out.println("ServiceId = " + serviceId);

            String bookStatus = serviceProviderRepository.getBookStatus(bookTb.getBookId());
            if (bookStatus == null) {
                throw new NotFoundException("Book status not found");
            }

            Double servicePrice = serviceProviderRepository.getServicePrice(serviceId);
            if (servicePrice == null) {
                throw new NotFoundException("Service price not found");
            }
            System.out.println("servicePrice = " + servicePrice);

            System.out.println("serviceId " + serviceId);

            Double discountPrice = serviceProviderRepository.getDisCountPrice(serviceId);
            double priceAfterPromotion;

            if (discountPrice != null) {
                priceAfterPromotion = servicePrice * discountPrice;
            } else {
                priceAfterPromotion = 0.0d;
            }
            double totalAmount = servicePrice - priceAfterPromotion;


            System.out.println("totalAmount =" +totalAmount);
            System.out.println("orderDate = " + orderDate );
            if (orderDate != null) {
                ServiceProviderHistoryResponse serviceProviderHistoryResponse = new ServiceProviderHistoryResponse(
                        userResponse.getUsername(),
                        userResponse.getEmail(),
                        userResponse.getProfile_img(),
                        bookTb.getBookId(),
                        orderDate,
                        bookStatus,
                        totalAmount
                );
                serviceProviderHistoryResponseList.add(serviceProviderHistoryResponse);

            }else{
                System.out.println("Order date not found, skipping bookTb with ID " + bookTb.getBookId());
                continue; // Skip to the next iteration if orderDate is null
            }
//            System.out.println("");
            System.out.println("serviceProviderHistoryResponseList" + serviceProviderHistoryResponseList);
//            return  serviceProviderHistoryResponseList;
        }
        System.out.println("bookTbList" + bookTbList);
        System.out.println("serviceProviderHistoryResponseList " + serviceProviderHistoryResponseList);
        return serviceProviderHistoryResponseList;
    }
}
