package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.RateFeedback;
import com.example.springfinalproject.model.Entity.ReteFeedbackBook;
import com.example.springfinalproject.model.Entity.FeedbackOrder;
import com.example.springfinalproject.model.request.FeedbackOnShopRequest;
import com.example.springfinalproject.model.request.RateFeedbackRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RateService {

    Integer postRateFeedback(RateFeedbackRequest rateFeedbackRequest,Integer orderId);

    List<FeedbackOrder> getAllRateFeedback();

    RateFeedback getRateFeedbackById(Integer feedbackId);

    List<ReteFeedbackBook> getAllRateFeedbackForServiceProvider();

    Integer postRateFeedbackBooking(RateFeedbackRequest rateFeedbackRequest, Integer bookId);

    RateFeedback getRateFeedbackByIdOnBooking(Integer feedbackId);

    List<FeedbackOnShopRequest> getAllFeedbackByShopId(Integer shopId);
}
