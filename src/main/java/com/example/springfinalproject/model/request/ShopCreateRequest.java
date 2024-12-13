package com.example.springfinalproject.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopCreateRequest {
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String shopName;
    @Min(value = 1, message = "Shop Type mush be bigger than 0")
    private Integer shopTypeId;

    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Wrong pattern Email")
    private String email;
    @JsonIgnore
    private String lat;
    @JsonIgnore
    private String longti;

    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    @Pattern(regexp = "^\\d{8,12}$", message = "Invalid phone number. It must be between 8 and 12 digits.")
    private String phoneNumber;

    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String shopProfileImg;

    @JsonIgnore
    private Integer userId;
    @JsonIgnore
    private boolean isActive = true;
}
