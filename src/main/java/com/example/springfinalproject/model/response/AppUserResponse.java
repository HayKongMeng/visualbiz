package com.example.springfinalproject.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppUserResponse {
    private Integer userId;
    private String userName;
    private String email;
    private String gender;
    private String profileImage;
    private String address;
    private String currentRole;
    private boolean isEmailVerify;
}
