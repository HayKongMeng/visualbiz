package com.example.springfinalproject.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRequest {
    private String userId;
    private String userName;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String location;
    private String profilePicture;
    private String coverImage;
}
