package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReteFeedbackBook {
    private Integer feedbackBookId;
    private String profileImg;
    private String customerName;
    private Integer bookId;
    private String feedback;
    private Double fullprice;
    private Double rate;
}
