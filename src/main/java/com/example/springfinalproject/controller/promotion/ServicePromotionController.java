package com.example.springfinalproject.controller.promotion;


import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Promotion;
import com.example.springfinalproject.model.dto.PromotionDto;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.OrderBookHistoryRequest;
import com.example.springfinalproject.model.request.PromotionRequest;
import com.example.springfinalproject.model.request.ServicePromotionRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.PromotionResponse;
import com.example.springfinalproject.model.response.ServicePromotionResponse;
import com.example.springfinalproject.model.response.ServiceProviderHistoryResponse;
import com.example.springfinalproject.services.PromotionService;
import com.example.springfinalproject.services.ServicePromotion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/serviceProvider")
@CrossOrigin
@AllArgsConstructor
public class ServicePromotionController {
    private final ServicePromotion servicePromotion;
    private final PromotionService promotionService;



    // post promotion
    @PostMapping
    @Operation(summary = "post promotion service (service provider)")
    public ResponseEntity<ApiResponse<List<ServicePromotionResponse>>> createPromotionForService(@Valid @RequestBody ServicePromotionRequest servicePromotionRequest) {
        ApiResponse<List<ServicePromotionResponse>> response;
        List<ServicePromotionResponse> productList  = servicePromotion.createPromotionForService(servicePromotionRequest);
        if(!productList.isEmpty()) {
            response = ApiResponse.<List<ServicePromotionResponse>>builder()
                    .message("Promotion created")
                    .payload(productList)
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = ApiResponse.<List<ServicePromotionResponse>>builder()
                    .message("Promotion not created")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @Operation(summary = "Get all service promotion (customer) ")
    public ResponseEntity<ApiResponse<List<PromotionTbWithId>>> getAllPromotions(@Valid @RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                                 @RequestParam(name = "size",defaultValue = "8") Integer size) {
        ApiResponse<List<PromotionTbWithId>> response = ApiResponse.<List<PromotionTbWithId>>builder()
                .message("Get all promotions")
                .payload(promotionService.getAllPromotion(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/owner")
    @Operation(summary = "Get all service promotion (service provider) ")
    public ResponseEntity<ApiResponse<List<ServicePromotionResponse>>> getAllPromotionsForServiceProvider(@Valid @RequestParam(defaultValue = "1") Integer page,
                                                                                 @RequestParam(defaultValue = "8") Integer size) {
        ApiResponse<List<ServicePromotionResponse>> response = ApiResponse.<List<ServicePromotionResponse>>builder()
                .message("Get all promotions")
                .payload(servicePromotion.getAllServicePromotionForServiceProvider(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/serviceProvider/history")
    @Operation(summary = "Get all history (service provider)")
    public ResponseEntity<ApiResponse<List<ServiceProviderHistoryResponse>>> getAllOrderHistoryServiceProviderPage(@Valid @RequestParam(defaultValue = "1") Integer page,
                                                                                                                   @RequestParam(defaultValue = "8") Integer size){
        ApiResponse<List<ServiceProviderHistoryResponse>> response = ApiResponse.<List<ServiceProviderHistoryResponse>>builder()
                .message("Get all book history successfully")
                .payload(servicePromotion.getAllBookHistory(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }



}
