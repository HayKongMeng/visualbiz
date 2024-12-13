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
public class BookmarkRequest {
    @JsonIgnore
    private Integer userId;
    private Integer productId;
    @JsonIgnore
    private  Integer bookmarkId;
}
