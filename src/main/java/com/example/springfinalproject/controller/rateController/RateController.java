package com.example.springfinalproject.controller.rateController;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.RateFeedback;
import com.example.springfinalproject.model.Entity.ReteFeedbackBook;
import com.example.springfinalproject.model.Entity.FeedbackOrder;
import com.example.springfinalproject.model.request.FeedbackOnShopRequest;
import com.example.springfinalproject.model.request.RateFeedbackRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.services.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/rate-feedback")
@CrossOrigin
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    //get all feedback by shop id for seller
    @GetMapping("/seller/currentShop")
    @Operation(summary = "get all order feedback by currentShop (seller)")
    public ResponseEntity<ApiResponse<List<FeedbackOrder>>> getAllRateFeedbackByCurrentShop() {
        ApiResponse<List<FeedbackOrder>> response = ApiResponse.<List<FeedbackOrder>>builder()
                .message("Get All Shop")
                .payload(rateService.getAllRateFeedback())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    // get feedback by shop Id
    @GetMapping("/shop/{shopId}")
    @Operation(summary = "Get feedback by shopId(customer)")
    public ResponseEntity<ApiResponse<List<FeedbackOnShopRequest>>> getRateFeedbackByShopId(@PathVariable @Positive Integer shopId) {
        ApiResponse<List<FeedbackOnShopRequest>> response = ApiResponse.<List<FeedbackOnShopRequest>>builder()
                .message("Get feedback by Shop successful")
                .payload(rateService.getAllFeedbackByShopId(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //view rating and feedback by id for seller
    @GetMapping("/seller/feedbackId/{feedbackId}")
    @Operation(summary = "View rating and feedback by id (seller)")
    public ResponseEntity<ApiResponse<RateFeedback>> getRateFeedbackById(@PathVariable("feedbackId") Integer feedbackId) {
        ApiResponse<RateFeedback> response = null;
        if (rateService.getRateFeedbackById(feedbackId) != null) {
            response = ApiResponse.<RateFeedback>builder()
                    .message("Get Order Feedback")
                    .payload(rateService.getRateFeedbackById(feedbackId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("orderId is null");
    }

    //post rate and feedback one order
    @PostMapping("/order/{orderId}")
    @Operation(summary = "rate and feedback on order (customer)")
    public ResponseEntity<ApiResponse<RateFeedback>> RateFeedback(@RequestBody RateFeedbackRequest rateFeedbackRequest,@PathVariable("orderId") Integer orderId) {
        ApiResponse<RateFeedback> response = null;
        Integer posted = rateService.postRateFeedback(rateFeedbackRequest,orderId);
        if (posted != null) {
            response = ApiResponse.<RateFeedback>builder()
                    .message("Post Order Feedback")
                    .payload(rateService.getRateFeedbackById(posted))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("orderId is null");
    }


    //get all rate feedback on book for service provider
    @GetMapping("/service/currentShop")
    @Operation(summary = "get all booking feedback by currentShop (Service Provider)")
    public ResponseEntity<ApiResponse<List<ReteFeedbackBook>>> getAllRateFeedbackByCurrentShopServiceProvider() {
        ApiResponse<List<ReteFeedbackBook>> response = ApiResponse.<List<ReteFeedbackBook>>builder()
                .message("Get All Shop")
                .payload(rateService.getAllRateFeedbackForServiceProvider())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //view rating and feedback by id for service
    @GetMapping("service/feedbackId/{feedbackId}")
    @Operation(summary = "View rating and feedback by id (service provider)")
    public ResponseEntity<ApiResponse<RateFeedback>> getRateFeedbackByIdOnBooking(@PathVariable("feedbackId") Integer feedbackId) {
        ApiResponse<RateFeedback> response = null;
        if (rateService.getRateFeedbackByIdOnBooking(feedbackId) != null) {
            response = ApiResponse.<RateFeedback>builder()
                    .message("Get Order Feedback")
                    .payload(rateService.getRateFeedbackByIdOnBooking(feedbackId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("orderId is null");
    }

    //post rate and feedback one book for service provider
    @PostMapping("/booking/{bookId}")
    @Operation(summary = "rate and feedback on book (customer)")
    public ResponseEntity<ApiResponse<RateFeedback>> RateFeedbackBook(@RequestBody RateFeedbackRequest rateFeedbackRequest,@PathVariable("bookId") Integer bookId) {
        ApiResponse<RateFeedback> response = null;
        Integer posted = rateService.postRateFeedbackBooking(rateFeedbackRequest,bookId);
        if (posted != null) {
            response = ApiResponse.<RateFeedback>builder()
                    .message("Post book Feedback")
                    .payload(rateService.getRateFeedbackByIdOnBooking(posted))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }else throw new NotFoundException("bookId is null");
    }
}
