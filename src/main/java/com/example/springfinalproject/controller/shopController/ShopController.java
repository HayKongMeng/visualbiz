package com.example.springfinalproject.controller.shopController;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.*;
import com.example.springfinalproject.model.request.*;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.repository.RoleRepository;
import com.example.springfinalproject.services.ShopService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/shop")
@AllArgsConstructor
@CrossOrigin
public class ShopController {
    private final ShopService shopService;
    private final RoleRepository roleRepository;

    //customer
    //Customer Get all shop
    @GetMapping
    @Operation(summary = "Customer Get all shop(Customer)")
    public ResponseEntity<ApiResponse<List<Shop>>> getAllShop(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10")@Positive  Integer size
    ){
        ApiResponse<List<Shop>> response = ApiResponse.<List<Shop>>builder()
                .message("Get Shop Successful")
                .payload(shopService.getAllShop(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Seller get all order from customer
    @GetMapping("/orders")
    @Operation(summary = "Seller get all orders(Seller)")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders(){
        ApiResponse<List<Order>> response = ApiResponse.<List<Order>>builder()
                .message("Get Shop Successful")
                .payload(shopService.getAllOrders())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //seller or service provider get their own shop
    //Get all Shop current user
    @GetMapping("/currentShop")
    @Operation(summary = "seller Get all shop by current user")
    public ResponseEntity<ApiResponse<Shop>> getShopCurrentUser(){
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Get Shop Successful")
                .payload(shopService.getShopCurrentUser())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Get Shop By id
    @GetMapping("/{id}")
    @Operation(summary = "Get shop by Id")
    public ResponseEntity<ApiResponse<Shop>> getShopById(@PathVariable @Positive Integer id){
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Get Shop by Id")
                .payload(shopService.getShopById(id))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Customer search shop by name
    //Get all shop by Name
    @GetMapping("/name/{name}")
    @Operation(summary = "Customer Get all shop by Name")
    public ResponseEntity<ApiResponse<List<Shop>>> getShopByName(@PathVariable @Valid String name){
        ApiResponse<List<Shop>> response = ApiResponse.<List<Shop>>builder()
                .message("Get shop By Name successfully")
                .payload(shopService.getShopByName(name))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Customer create shop
    //Create new shop
    @PostMapping("/create-shop")
    @Operation(summary = "Customer Create new shop")
    public ResponseEntity<ApiResponse<Shop>> createShop(@RequestParam String lat,
                                                        @RequestParam String longitude,@RequestBody @Valid ShopCreateRequest shopCreateRequest){
        Integer shopId = shopService.createNewShop(lat,longitude,shopCreateRequest);
        System.out.println(shopId);
        System.out.println("Print shop id"+shopService.getShopById(shopId));
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Create shop successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        return ResponseEntity.ok(response);
    }

    //seller set day off
    //Post Day off
    @PutMapping("/shop/day-off")
    @Operation(summary = "seller Post Day off")
    public ResponseEntity<ApiResponse<Shop>> postDayOffShop(@RequestBody @Valid ShopDayOffRequest shopDayOffRequest){
        Integer shopId = shopService.postShopDayOff(shopDayOffRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Set day off successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //seller upload profile
    //Upload Profile image
    @PutMapping( "/upload-profile")
    @Operation(summary = "seller upload image")
    public ResponseEntity<?> postImage(@RequestBody @Valid ShopProfileRequest shopProfileRequest){
        Integer shopId = shopService.uploadShopImg(shopProfileRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Upload image successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }





    //seller upload shop cover
    //UPLOAD SHOP COVER IMG
    @PutMapping
    @Operation(summary = "Seller Upload shop cover")
    public ResponseEntity<ApiResponse<Shop>> updateShopCover(@RequestBody @Valid ShopCoverImageRequest shopCoverImageRequest){
        Integer shopId = shopService.uploadShopCoverImg(shopCoverImageRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Upload cover image successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //seller update shop info
    //Update Shop info
    @PutMapping("/update-shop-info")
    @Operation(summary = "Seller Update Shop info")
    public ResponseEntity<ApiResponse<Shop>> updateShop( @RequestBody @Valid ShopUpdateRequest shopUpdateRequest){
        Integer shopId = shopService.updateShop(shopUpdateRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Get shop info successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Seller update description
    //Update Shop Description
    @PutMapping("/update-shop-description")
    @Operation(summary = "Seller Update Shop Description")
    public ResponseEntity<ApiResponse<Shop>> updateShopDescription(@RequestBody @Valid ShopUpdateDescriptionRequest shopUpdateDescriptionRequest){
        Integer shopId = shopService.updateShopDescription(shopUpdateDescriptionRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Update Shop Description successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                  .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //update location on seller
    //Update location shop at shop management
    @PutMapping("/update-location")
    @Operation(summary = "seller location update ")
    public ResponseEntity<ApiResponse<Shop>> updateLocation(@RequestBody ShopLocationRequest shopLocationRequest){
        Integer shopId = shopService.updateLocation(shopLocationRequest);
        ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                .message("Update Shop Location successfully")
                .payload(shopService.getShopById(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //seller shop management
    //Delete Shop
    @PutMapping("/delete-shop")
    @Operation(summary = "Seller Delete Shop")
    public ResponseEntity<ApiResponse<Shop>> deleteShop(){
        boolean result = shopService.deleteShop();
        ApiResponse<Shop> response =null;
        if (!result){
             response = ApiResponse.<Shop>builder()
                    .message("Delete shop successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }


    // seller shop management
    //Activate Shop That already have
    @PutMapping("/activate-shop")
    @Operation(summary = "Seller Activate Shop That already have")
    public ResponseEntity<ApiResponse<Shop>> activateShop(){
        boolean result = shopService.activateShop();
        System.out.println(result);
        if (result){
            ApiResponse<Shop> response = ApiResponse.<Shop>builder()
                    .message("Activate Shop successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);

        }
        return null;
    }


    //seller
    //Dashboard
    @GetMapping("/total/dashboard")
    @Operation(summary = "Dashboard seller get total seller")
    public ResponseEntity<ApiResponse<ShopTotalRequest>> getTotalSell(){
        ApiResponse<ShopTotalRequest> response = ApiResponse.<ShopTotalRequest>builder()
                .message("Update Shop Location successfully")
                .payload(shopService.getTotalInDashboard())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get feedback dashboard seller
    @GetMapping("/feedback/dashboard")
    @Operation(summary = "Dashboard seller get feedback seller")
    public ResponseEntity<ApiResponse<List<ShopFeedbackRequest>>> getFeedbackSell(){
        ApiResponse<List<ShopFeedbackRequest>> response = ApiResponse.<List<ShopFeedbackRequest>>builder()
                .message("Get Feedback on dashboard successfully")
                .payload(shopService.getFeedbackSeller())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //filter popular product shop by rate
    @GetMapping("/popular/product/shop")
    @Operation(summary = "Get all popular product shop by rate (customer)")
    public ResponseEntity<ApiResponse<List<PopularShop>>> getPopularShopByRate(){
        ApiResponse<List<PopularShop>> response = ApiResponse.<List<PopularShop>>builder()
                .message("Get all popular shop by rate successfully")
                .payload(shopService.getPopularShopByRate())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //filter popular service shop by rate
    @GetMapping("/popular/service/shop")
    @Operation(summary = "Get all popular service shop by rate (customer)")
    public ResponseEntity<ApiResponse<List<PopularShop>>> getPopularServiceShopByRate(){
        ApiResponse<List<PopularShop>> response = ApiResponse.<List<PopularShop>>builder()
                .message("Get all popular shop by rate successfully")
                .payload(shopService.getPopularServiceShopByRate())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //get sum of rating for product shop
    @GetMapping("/rating/product/shop/{shopId}")
    @Operation(summary = "Get AVG of rating for product shop by shop id (customer)")
    public ResponseEntity<ApiResponse<PopularShop>> getRatingProductShopByShopId(@PathVariable Integer shopId){
        ApiResponse<PopularShop> response = null;
        PopularShop popularShop = shopService.getRatingProductShopByShopId(shopId);
//        System.out.println(popularShop);
        if (popularShop != null){
            response = ApiResponse.<PopularShop>builder()
                    .message("Get rating for product shop by shop id (customer)")
                    .payload(popularShop)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("Get rating for product shop by shop id not found");
    }

    //get sum of rating for product shop
    @GetMapping("/rating/service/shop/{shopId}")
    @Operation(summary = "Get AVG of rating for service shop by shop id (customer)")
    public ResponseEntity<ApiResponse<PopularShop>> getRatingServiceShopByShopId(@PathVariable Integer shopId){
        ApiResponse<PopularShop> response = null;
        PopularShop popularShop = shopService.getRatingServiceShopByShopId(shopId);
//        System.out.println(popularShop);
        if (popularShop != null){
            response = ApiResponse.<PopularShop>builder()
                    .message("Get rating for service shop by shop id (customer)")
                    .payload(popularShop)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("Get rating for service shop by shop id not found");
    }

    //get overview of service provider
    @GetMapping("/service/provider/overview")
    @Operation(summary = "Service provider overview (service provider shop)")
    public ResponseEntity<ApiResponse<OverviewServiceProvider>> getOverviewServiceProvider(){
        ApiResponse<OverviewServiceProvider> response = ApiResponse.<OverviewServiceProvider>builder()
                .message("Get overview service provider successfully")
                .payload(shopService.getOverviewServiceProvider())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
}
