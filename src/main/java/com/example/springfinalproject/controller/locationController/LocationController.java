package com.example.springfinalproject.controller.locationController;

import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.services.ShopService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/findNearestLocation")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin

public class LocationController {
    private final ShopService shopService;
    @GetMapping("/findNearestLocation")
    public ResponseEntity<ApiResponse<List<Shop>>> findNearestLocation(@RequestParam String lat,
                                                                       @RequestParam String longitude) {
        ApiResponse<List<Shop>> response = ApiResponse.<List<Shop>>builder()
                .message("Get shop By Name successfully")
                .payload(shopService.findNearestLocation(lat, longitude))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

}
