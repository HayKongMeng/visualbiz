package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookmarkServiceRequest {
    @JsonIgnore
    private Integer userId;
    private Integer serviceId;
    @JsonIgnore
    private  Integer bookmarkId;
}
