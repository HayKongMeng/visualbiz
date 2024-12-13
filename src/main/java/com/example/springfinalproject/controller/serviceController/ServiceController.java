package com.example.springfinalproject.controller.serviceController;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.ServiceApp;
import com.example.springfinalproject.model.request.ServiceAppRequest;
import com.example.springfinalproject.model.request.ServiceScheduleRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.services.ServiceAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/service")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class ServiceController {
    private final ServiceAppService serviceAppService;

    //Get all service for customer
    @GetMapping
    @Operation(summary = "Get all service (customer)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getAllServices(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10")@Positive  Integer size
    ) {
        ApiResponse<List<ServiceApp>> response = ApiResponse.<List<ServiceApp>>builder()
                .message("Get All Service")
                .payload(serviceAppService.getAllService(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //get service by id for customer
    @GetMapping("/{id}")
    @Operation(summary = "Get service by id (customer)")
    public ResponseEntity<ApiResponse<ServiceApp>> getServiceByIdForCustomer(@PathVariable("id") @Positive Integer id){
        ApiResponse<ServiceApp> response = null;
        if (serviceAppService.getServiceByIdForCustomer(id) != null) {
            response = ApiResponse.<ServiceApp>builder()
                    .message("Get Service By Id "+id+" is successful.")
                    .payload(serviceAppService.getServiceByIdForCustomer(id))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else throw new NotFoundException("Service ID "+id+" does not exist");
        return ResponseEntity.ok(response);
    }
    //get service by id for customer
    @GetMapping("/name/{name}")
    @Operation(summary = "Get service by NAME (customer)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getServiceByNameForCustomer(@Valid @PathVariable("name") @NotNull String name){

            ApiResponse<List<ServiceApp>> response = null;
            if (serviceAppService.getServiceByNameForCustomer(name)!=null){
                response = ApiResponse.<List<ServiceApp>>builder()
                        .message("Get Service By NAME "+name+" is successful.")
                        .payload(serviceAppService.getServiceByNameForCustomer(name))
                        .httpStatus(HttpStatus.OK)
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .build();
            }else throw new NotFoundException("Service Name "+name+" does not exist");
        return ResponseEntity.ok(response);
    }


    //get all service by shopId for customer
    @GetMapping("/serviceShopId/{shopId}")
    @Operation(summary = "Get service by shop id (customer)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getServiceByShopId(@PathVariable @Positive Integer shopId,
                                                                      @RequestParam(name = "page",defaultValue = "1") @Positive Integer page,
                                                                      @RequestParam(name = "size",defaultValue = "10") @Positive  Integer size) {
        ApiResponse<List<ServiceApp>> response = null;
        List<ServiceApp> serviceAppList = serviceAppService.getAllServiceByshopIdWithPagination(shopId,page,size);
        if (serviceAppList!=null) {
            response = ApiResponse.<List<ServiceApp>>builder()
                    .message("Get Service By Shop Id "+shopId+" dose not exist")
                    .payload(serviceAppList)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else throw new NotFoundException("Shop id "+shopId+" does not exist");
        return ResponseEntity.ok(response);
    }

    //Get all service in shop for current user
    @GetMapping("/shopService")
    @Operation(summary = "Get All service in your shop (service provider)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getAllServiceByCurrentUser(
    ){
        ApiResponse<List<ServiceApp>> response = ApiResponse.<List<ServiceApp>>builder()
                .message("Get All Service in shop")
                .payload(serviceAppService.getAllServiceByCurrentUser())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Get Service by id for service provider
    @GetMapping("/idInShop/{id}")
    @Operation(summary = "Get service by ID (service provider)")
    public ResponseEntity<ApiResponse<ServiceApp>> getServiceById(@PathVariable @Positive Integer id) {
        ApiResponse<ServiceApp> response = ApiResponse.<ServiceApp>builder()
                .message("Get All Event")
                .payload(serviceAppService.getServiceByIdInShop(id))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get service by name
    @GetMapping("/nameInShop/{name}")
    @Operation(summary = "Get service in shop by name (service provider)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getServiceByName(@Valid @PathVariable("name") String name,
                                                                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                          @RequestParam(name = "size",defaultValue = "10") Integer size) {
        ApiResponse<List<ServiceApp>> response = null;
        if (serviceAppService.getServiceByName(name,page,size)!=null){
            response = ApiResponse.<List<ServiceApp>>builder()
                    .message("Get service in shop by name "+name+" is successful.")
                    .payload(serviceAppService.getServiceByName(name,page,size))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else throw new NotFoundException("Service name "+name+" does not exist in shop");
        return ResponseEntity.ok(response);
    }

    //update service by id
    @PutMapping("/{id}")
    @Operation(summary = "update service by id (service provider)")
    public ResponseEntity<ApiResponse<ServiceApp>> updateServiceById(@Valid @RequestBody ServiceAppRequest serviceAppRequest ,
                                                                     @PathVariable @Positive Integer id){
        ApiResponse<ServiceApp> response = null;

        Integer udpatedId = serviceAppService.updateServiceById(serviceAppRequest,id);
        if (udpatedId!=null){
            response = ApiResponse.<ServiceApp>builder()
                    .message("Update product by id "+udpatedId+" is successful")
                    .payload(serviceAppService.getServiceByIdInShop(udpatedId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else throw new NotFoundException("Update product by id "+udpatedId+" does not exist in shop");
        return ResponseEntity.ok(response);
    }

    //Delete service by id for current shop
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete service by id just change is_active = true to false (service provider)")
    public ResponseEntity<ApiResponse<ServiceApp>> deleteServiceById(@PathVariable("id") List<Integer> id) {
        ApiResponse<ServiceApp> response = ApiResponse.<ServiceApp>builder()
                    .message("Delete service by id "+id+" is successful")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
    }

    //get all service by category id for customer
    @GetMapping("/product-by-categoryId/{id}")
    @Operation(summary = "Get all service by category id (customer)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getAllServiceByCategoryIdForCustomer(@PathVariable @Positive Integer id,
                                                                                              @RequestParam(name = "page",defaultValue = "1")@Positive Integer page,
                                                                                              @RequestParam(name = "size",defaultValue = "10")@Positive Integer size){
        ApiResponse<List<ServiceApp>> response = null;
        List<ServiceApp> serviceAppList = serviceAppService.getAllServiceByCategoryIdForCustomer(id,page,size);
        if (serviceAppList!=null && !serviceAppList.isEmpty()){
            response = ApiResponse.<List<ServiceApp>>builder()
                    .message("Get service by category id "+id+" is successful")
                    .payload(serviceAppList)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("Get service by category id "+id+" does not exist");
    }

    //get service by category id
    @GetMapping("/ServiceByCategoryId/{id}")
    @Operation(summary = "Get service by category id (service provider)")
    public ResponseEntity<ApiResponse<List<ServiceApp>>> getServiceByCategoryId(@PathVariable @Positive Integer id,
                                                                                @RequestParam(name = "page",defaultValue = "1") Integer page,
                                                                                @RequestParam(name = "size",defaultValue = "10") Integer size) {
        ApiResponse<List<ServiceApp>> response = null;
        List<ServiceApp> serviceList = serviceAppService.getAllServiceByCategoryId(id,page,size);
        if (serviceList!=null && !serviceList.isEmpty()){
            response = ApiResponse.<List<ServiceApp>>builder()
                    .message("Get service by category id "+id+" is successful")
                    .payload(serviceList)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("Get service by category id "+id+" does not exist in shop");
    }

    //    Create new service in shop
    @PostMapping
    @Operation(summary = "Create new service in shop")
    public ResponseEntity<ApiResponse<ServiceApp>> createService(@Valid @RequestBody ServiceAppRequest serviceAppRequest) {
        Integer serviceId = serviceAppService.postNewService(serviceAppRequest);
        ApiResponse<ServiceApp> response = ApiResponse.<ServiceApp>builder()
                .message("Create new service successfully")
                .payload(serviceAppService.getServiceByIdInShop(serviceId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
}

