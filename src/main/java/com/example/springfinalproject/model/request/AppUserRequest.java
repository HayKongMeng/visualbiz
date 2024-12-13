package com.example.springfinalproject.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {

    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "password must be at least 8 characters long and include both letters and numbers"
    )
    private String password;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "confirm password must be at least 8 characters long and include both letters and numbers"
    )
    private String confirmPassword;
}
