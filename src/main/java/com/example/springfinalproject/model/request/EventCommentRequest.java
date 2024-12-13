package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventCommentRequest {
    private Integer eventId;
    private String commentDescription;
    @JsonIgnore
    private LocalDateTime commentDate;
    @JsonIgnore
    private Integer userId;
}
