package com.example.springfinalproject.services;

import com.example.springfinalproject.model.request.ProductWithQtyRequest;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailService {
    void sendMail(String optCode, String email, String subject) throws MessagingException;

    void sendInvoice(String profileImg, String username, LocalDateTime dateTime, Integer orderId, String shopName, String shopAddress, String email, List<ProductWithQtyRequest> products) throws MessagingException;
}
