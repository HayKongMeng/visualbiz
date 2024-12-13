package com.example.springfinalproject.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopUpdateRequest {
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    private String shopName;

    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Wrong pattern Email")
    private String email;

    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    @Pattern(regexp = "^\\d{8,12}$", message = "Invalid phone number. It must be between 8 and 12 digits.")
    private String phoneNumber;

    @JsonIgnore
    private Integer shopId;
}
