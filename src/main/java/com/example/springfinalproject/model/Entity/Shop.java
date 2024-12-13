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
public class Shop {
    private Integer shopId;
    private String shopName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String shopProfileImg;
    private String shopProfileCover;
    private String lat;
    private String longitude;
    private String phoneNumber;
    private String description;
    private AppUser user;
    private ShopType shopType;
    private String email;
    private boolean isActive;
    private boolean isAvailable = true;


}
