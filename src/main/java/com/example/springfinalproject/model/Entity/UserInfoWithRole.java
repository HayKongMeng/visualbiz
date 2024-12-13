package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserInfoWithRole {
    private Integer userId;
    private String userName;
    private String email;
    private String gender;
    private String profileImage;
    private String address;
    private List<?> currentRole;
}
