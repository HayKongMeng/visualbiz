package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Otp {
    private Integer otpId;
    private String otpCode;
    private LocalDateTime issuedAt;
    private LocalDateTime expiration;
    private Boolean verify;
    private Integer userId;
    private boolean forgetPassword;
}
