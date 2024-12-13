package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FeedbackOrder {
    private Integer feedbackOrderId;
    private String profileImg;
    private String customerName;
    private Integer orderId;
    private String feedback;
    private Double fullprice;
    private Double rate;
}
