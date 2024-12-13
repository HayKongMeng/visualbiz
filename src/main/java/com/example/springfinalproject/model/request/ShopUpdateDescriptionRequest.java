package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopUpdateDescriptionRequest {
    private String description;
    @JsonIgnore
    private Integer shopId;
}
