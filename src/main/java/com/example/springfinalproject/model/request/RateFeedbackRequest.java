package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RateFeedbackRequest {
    private String feedback;
    @Size(min = 1, max = 5 , message = "rate should in range of 1 to 5")
    private Integer rate;
    private LocalDateTime dateTime;
    @JsonIgnore
    private Integer userId;
}
