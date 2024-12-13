package com.example.springfinalproject.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RateFeedback {
    private Integer feedbackId;
    private String feedback;
    private Integer rate;
    private Integer customerId;
    private LocalDateTime dateTime;
}
