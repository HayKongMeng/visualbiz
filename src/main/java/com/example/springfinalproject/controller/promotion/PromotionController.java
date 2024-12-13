package com.example.springfinalproject.controller.promotion;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.dto.PromotionDto;
import com.example.springfinalproject.model.dto.PromotionTbWithId;
import com.example.springfinalproject.model.request.PromotionRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.ProductResponse;
import com.example.springfinalproject.model.response.PromotionResponse;
import com.example.springfinalproject.services.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/promotion")
@AllArgsConstructor
@CrossOrigin
public class PromotionController {
    private final PromotionService promotionService;
    //get shop that have promotion
    @GetMapping
    @Operation(summary = "get shop that have promotion (customer) ")
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



    //get all promotion by shop id
    @GetMapping("/shop-id/{shopId}")
    @Operation(summary = "Get all promotion by shop id (seller)(customer)")
    public ResponseEntity<ApiResponse<List<PromotionTbWithId>>> getPromotionShopId(@Valid @PathVariable("shopId") Integer shopId,
                                                                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                           @RequestParam(name = "size",defaultValue = "10") Integer size){
        ApiResponse<List<PromotionTbWithId>> response = null;
        List<PromotionTbWithId> promotionList = promotionService.getPromotionShopId(shopId,page,size);
        System.out.println(promotionList);
        if (!promotionList.isEmpty()) {
            response = ApiResponse.<List<PromotionTbWithId>>builder()
                    .message("Get all promotion by shop id "+shopId+" is successful.")
                    .payload(promotionService.getPromotionShopId(shopId,page,size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else throw new NotFoundException("Shop's id "+shopId + " does not exist.");
        return ResponseEntity.ok(response);
    }

    //get all promotion by current user
    @GetMapping("/current/shop")
    @Operation(summary = "Get all promotion by current shop")
    public ResponseEntity<ApiResponse<List<PromotionTbWithId>>> getPromotionCurrentShop(
                                                                                   @RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                                   @RequestParam(name = "size",defaultValue = "10") Integer size){
        ApiResponse<List<PromotionTbWithId>> response = null;
        List<PromotionTbWithId> promotionList = promotionService.getPromotionCurrentShop(page,size);
        System.out.println(promotionList);
        if (!promotionList.isEmpty()) {
            response = ApiResponse.<List<PromotionTbWithId>>builder()
                    .message("Get all promotion by current shop is successful.")
                    .payload(promotionService.getPromotionCurrentShop(page,size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //get promotion by id
    @GetMapping("/{id}")
    @Operation(summary = "get promotion by id")
    public ResponseEntity<ApiResponse<PromotionDto>> getPromotionById(@Positive @PathVariable("id") Integer promotionId) {
        ApiResponse<PromotionDto> response = null;
        if (promotionService.getPromotionById(promotionId)!=null){
            response = ApiResponse.<PromotionDto>builder()
                    .message("Get promotion by id "+promotionId)
                    .payload(promotionService.getPromotionById(promotionId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = ApiResponse.<PromotionDto>builder()
                    .message("Get promotion by id "+promotionId+" not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

    // post promotion
    @PostMapping
    @Operation(summary = "Post promotion product (seller)")
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> createPromotionForProduct(@Valid @RequestBody PromotionRequest promotionRequest) {

        ApiResponse<List<PromotionResponse>> response;
        List<PromotionResponse> productList  = promotionService.createPromotionForProduct(promotionRequest);
        if(!productList.isEmpty()) {
            response = ApiResponse.<List<PromotionResponse>>builder()
                    .message("Promotion created")
                    .payload(productList)
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = ApiResponse.<List<PromotionResponse>>builder()
                    .message("Promotion not created")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //update promotion
    @PutMapping("/{id}")
    @Operation(summary = "Update promotion (seller)")
    public ResponseEntity<ApiResponse<PromotionDto>> updatePromotionById(@Valid @RequestBody PromotionRequest promotionRequest, @PathVariable("id") Integer promotionId){
        ApiResponse<PromotionDto> response = null;
        Integer updateId = promotionService.updatePromotionById(promotionRequest,promotionId);
        System.out.println(updateId);
        if(updateId!=null) {
            response = ApiResponse.<PromotionDto>builder()
                    .message("Promotion id "+updateId+" updated")
                    .payload(promotionService.getPromotionById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new BadRequestException("Promotion id "+updateId+" not found");
    }
    @GetMapping("/get/oldest-promotion")
    @Operation(summary = "Get promotion Oldest (seller)")
    public ResponseEntity<ApiResponse<List<PromotionTbWithId>>> getEventOldest(){
        ApiResponse<List<PromotionTbWithId>> response = ApiResponse.<List<PromotionTbWithId>>builder()
                .message("Get Event order or end date")
                   .payload(promotionService.getOldesPromotion())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/thisweek-promotion")
    @Operation(summary = "Get promotion in this week (seller)")
    public ResponseEntity<ApiResponse<List<PromotionTbWithId>>> getThisWeekPromotion(){
        ApiResponse<List<PromotionTbWithId>> response = ApiResponse.<List<PromotionTbWithId>>builder()
                .message("Get Event order or end date")
                .payload(promotionService.getThisWeekPromotion())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete promotion (seller)")
    public ResponseEntity<ProductResponse<PromotionTbWithId>> deletePromotionForSeller(@RequestParam Integer promotionId) {
        ProductResponse<PromotionTbWithId> response = ProductResponse.<PromotionTbWithId>builder()
                .message("Delete product id " + promotionId + " is successful.")
                .payload(promotionService.deletePromotionWithId(promotionId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

}
