package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopFeedbackRequest {
    private Integer feedbackId;
    private String profileImg;
    private String username;
    private String productName;
    private Double unitPrice;
}
