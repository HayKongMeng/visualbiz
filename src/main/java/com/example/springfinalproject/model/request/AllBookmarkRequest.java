package com.example.springfinalproject.model.request;

import com.example.springfinalproject.model.Entity.Category;
import com.example.springfinalproject.model.Entity.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AllBookmarkRequest {
    private Integer bookmarkId;
    private Integer eventId;
    private Integer serviceId;
    private Integer productId;
    private ProductDetails productDetails;
    private ServiceDetails serviceDetails;
    private EventDetails eventDetails;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProductDetails {
        @JsonProperty("productName")
        private String productName;
        @JsonProperty("unitPrice")
        private Double unitPrice;
        @JsonProperty("productImg")
        private String productImg;
        @JsonProperty("productDescription")
        private String productDescription;
        @JsonProperty("productQty")
        private Integer productQty;
        @JsonProperty("expireDate")
        private LocalDateTime expireDate;
        @JsonProperty("isActive")
        private boolean isActive;
        @JsonProperty("barCode")
        private String barCode;
        @JsonProperty("category")
        private Category category;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ServiceDetails {
        @JsonProperty("serviceName")
        private String serviceName;
        @JsonProperty("servicePrice")
        private Double servicePrice;
        @JsonProperty("serviceImg")
        private String serviceImg;
        @JsonProperty("serviceDescription")
        private String serviceDescription;
        @JsonProperty("category")
        private Category category;
        @JsonProperty("isActive")
        private boolean isActive;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventDetails {
        @JsonProperty("eventTitle")
        private String eventTitle;
        @JsonProperty("eventDescription")
        private String eventDescription;
        @JsonProperty("eventImg")
        private String eventImg;
        @JsonProperty("eventAddress")
        private String eventAddress;
        @JsonProperty("startDate")
        private LocalDateTime startDate;
        @JsonProperty("endDate")
        private LocalDateTime endDate;
        @JsonProperty("shop")
        private Shop shop;
    }
}

