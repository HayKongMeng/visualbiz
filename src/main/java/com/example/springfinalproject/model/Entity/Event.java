package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Event {
    private Integer eventId;
    private String eventImage;
    private String eventDescription;
    private String eventAddress;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Shop shop;
    private String eventTitle;
}
