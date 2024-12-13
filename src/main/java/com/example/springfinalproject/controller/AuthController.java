package com.example.springfinalproject.controller;


import com.example.springfinalproject.model.Entity.UserInfoWithRole;
import com.example.springfinalproject.model.request.AppUserRequest;
import com.example.springfinalproject.model.request.AuthRequest;
import com.example.springfinalproject.model.request.PasswordRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.AppUserResponse;
import com.example.springfinalproject.model.response.AuthResponse;
import com.example.springfinalproject.model.response.UserResponse;
import com.example.springfinalproject.services.AuthService;
import com.example.springfinalproject.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/auths")
@AllArgsConstructor
@CrossOrigin
public class AuthController {
    private AuthService authService;
    private UserService userService;
    @PostMapping("/login")
    @Operation(summary = "login with username and email")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
    @PostMapping("/register")
    @Operation(summary = "fill user information")
    public ResponseEntity<?> register(@Valid @RequestBody AppUserRequest appUserRequest) throws MessagingException {
        AppUserResponse appUserResponse = authService.register(appUserRequest);
        ApiResponse<?> response = ApiResponse.builder()
                .message("Register Successfully")
                .httpStatus(HttpStatus.OK)
                .payload(appUserResponse)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/verify")
    @Operation(summary = "Verify account with otpCode (/forgot-password/verify)")
    public ResponseEntity<?> verify(@RequestParam @Positive(message = "Otp code cannot be negative , zero or letter") String otpCode) {
        authService.verify(otpCode);
        ApiResponse<?> response = ApiResponse.builder()
                .message("Your account is verify successful")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/resend")
    @Operation(summary = "Input email to resend otpCode(use with resend and forget password)")
    public ResponseEntity<?> resend(@RequestParam String email) throws MessagingException {
        authService.resend(email);
        ApiResponse<?> response = ApiResponse.builder()
        .message("Resend OTP code successfully")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }
    //imput only email to send otp for set up new password
    @PutMapping("/forget")
    @Operation(summary = "Set the new password with email (forgot-password/new-password),")
    public ResponseEntity<?> forget(@RequestParam String email, @Valid @RequestBody PasswordRequest passwordRequest) throws  MessagingException{
       boolean isForget = authService.forget(email, passwordRequest);
        System.out.println("isForget controller" + isForget);
        ApiResponse<?> response = ApiResponse.builder()
                .message("Your password is reset successful")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Reset the new password after login")
    public ResponseEntity<?> reset( @Valid @RequestBody PasswordRequest passwordRequest) {
        authService.reset( passwordRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Your password is reset successful");
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get the user information and role")
    public ResponseEntity<?> getUserInfoRole() {
        UserInfoWithRole userInfoWithRole = authService.userInfoWithRole();
        return ResponseEntity.status(HttpStatus.OK).body(userInfoWithRole);
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Get user information with id")
    public ResponseEntity<?> getUserInformation(Integer userId) {
        UserResponse userResponse = userService.getUserInformationWithId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @PostMapping("/login/google")
    @Operation(summary = "user login with google account")
    public ResponseEntity<?> loginWithGoogle(@Valid @RequestBody AuthRequest authRequest) throws Exception {
//        AuthResponse authResponse = authService.login(authRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
