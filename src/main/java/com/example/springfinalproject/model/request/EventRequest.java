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
public class EventRequest {
    private String eventTitle;
    private String eventDescription;
    private String eventImage;
    private String eventAddress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @JsonIgnore
    private Integer shopId;

}
