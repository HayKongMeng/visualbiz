package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String otpCode;
    private LocalDateTime issueAt;
    private LocalDateTime expiration;
    private Boolean verify;
    private  Integer userId;

}
