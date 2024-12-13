package com.example.springfinalproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private String user_id;
    private String username;
    private String gender;
    private String address;
    private String profile_img;
    private String cover_image;
    private String email;
    private String date_of_birth;
}
