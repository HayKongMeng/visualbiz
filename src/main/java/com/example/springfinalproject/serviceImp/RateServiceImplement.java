package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.RateFeedback;
import com.example.springfinalproject.model.Entity.ReteFeedbackBook;
import com.example.springfinalproject.model.Entity.FeedbackOrder;
import com.example.springfinalproject.model.request.FeedbackOnShopRequest;
import com.example.springfinalproject.model.request.RateFeedbackRequest;
import com.example.springfinalproject.repository.OrderRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.RateRepository;
import com.example.springfinalproject.services.RateService;
import com.example.springfinalproject.utils.GetCurrentUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RateServiceImplement implements RateService {
    private final RateRepository rateRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public RateServiceImplement(RateRepository rateRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.rateRepository = rateRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    //post feedback order of customer
    @Override
    public Integer postRateFeedback(RateFeedbackRequest rateFeedbackRequest, Integer orderId) {
        Integer userId = GetCurrentUser.currentId();

        Integer orderIdGotFromDB = rateRepository.getOrderById(orderId,userId);
        System.out.println(orderIdGotFromDB);
        if (orderIdGotFromDB == null) {
            throw new NotFoundException("Order not found");
        }
        Integer feedbackOrderId = rateRepository.getRateFeedbackByUserId(orderIdGotFromDB,userId);
        rateFeedbackRequest.setUserId(userId);
        if (feedbackOrderId != null){
            throw new BadRequestException("Rate Feedback already exists");
        }

        Integer feedbackId = rateRepository.postRateFeedback(rateFeedbackRequest);
        rateRepository.saveOrderFeedback(feedbackId, orderIdGotFromDB);
        return feedbackId;
    }

    //get all feedback order for seller
    @Override
    public List<FeedbackOrder> getAllRateFeedback() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            return rateRepository.getAllProductForDashboard(shopId);
        } else throw new BadRequestException("Please create shop");
    }

    //get feedback by feedback id
    @Override
    public RateFeedback getRateFeedbackById(Integer feedbackId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId==null){
            throw new BadRequestException("Shop not found");
        }
        if (feedbackId == null) {
            throw new NotFoundException("Get rate feedback by id " + feedbackId + " not found");
        }
        return rateRepository.getRateFeedbackById(feedbackId,shopId);
    }


    //get all feedback service for service provider
    @Override
    public List<ReteFeedbackBook> getAllRateFeedbackForServiceProvider() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId != null) {
            return rateRepository.getAllRateFeedbackForServiceProvider(shopId);
        } else throw new BadRequestException("Please create shop");
    }

    //post feedback booking for service provider
    @Override
    public Integer postRateFeedbackBooking(RateFeedbackRequest rateFeedbackRequest, Integer bookId) {
        //Fetch data
        LocalDateTime now = LocalDateTime.now();
        Integer userId = GetCurrentUser.currentId();
        rateFeedbackRequest.setUserId(userId);
        rateFeedbackRequest.setDateTime(now);

        Integer bookIdGotFromDB = rateRepository.getBookById(bookId,userId);
        System.out.println(bookIdGotFromDB);
        if (bookIdGotFromDB == null) {
            throw new NotFoundException("Booking not found");
        }
        Integer feedbackBookId = rateRepository.getRateFeedbackByUserIdForBooking(bookIdGotFromDB,userId);

        if (feedbackBookId != null){
            throw new BadRequestException("Rate Feedback already exists");
        }

        Integer feedbackId = rateRepository.postRateFeedbackBooking(rateFeedbackRequest);
        rateRepository.saveBookFeedback(feedbackId, bookIdGotFromDB);
        return feedbackId;
    }

    //get feedback booking by feedback id
    @Override
    public RateFeedback getRateFeedbackByIdOnBooking(Integer feedbackId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = productRepository.getShopByUserId(userId);
        if (shopId==null){
            throw new BadRequestException("Shop not found");
        }
        if (feedbackId == null) {
            throw new NotFoundException("Get rate feedback by id " + feedbackId + " not found");
        }
        return rateRepository.getRateFeedbackByIdOnBooking(feedbackId,shopId);
    }

    @Override
    public List<FeedbackOnShopRequest> getAllFeedbackByShopId(Integer shopId) {
        return rateRepository.findAllFeedbackByShopId(shopId);
    }

}
