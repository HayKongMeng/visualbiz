package com.example.springfinalproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserWithRoleResponse {
    private Integer userId;
    private String userName;
    private String email;
    private String gender;
    private String profileImage;
    private String address;
    private List<String> currentRole;
}
