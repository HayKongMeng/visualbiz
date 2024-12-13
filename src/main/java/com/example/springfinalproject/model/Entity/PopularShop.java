package com.example.springfinalproject.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PopularShop {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Shop shop;
    private Double shopRating;
}
