package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventDetail {
    private Integer eventId;
    private String eventImage;
    private String eventDescription;
    private String eventAddress;
    private Date startDate;
    private Date endDate;
    private String eventTitle;
    private List<Comment> comment;
}
