package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FeedbackOnShopRequest {
    private Integer feedbackId;
    private Float rate;
    private String profileImg;
    private String username;
    private String feedback;
    private LocalDateTime feedbackDate;
}
