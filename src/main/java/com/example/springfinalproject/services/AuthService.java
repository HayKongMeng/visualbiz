package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.UserInfoWithRole;
import com.example.springfinalproject.model.request.AppUserRequest;
import com.example.springfinalproject.model.request.AuthRequest;
import com.example.springfinalproject.model.request.PasswordRequest;
import com.example.springfinalproject.model.response.AppUserResponse;
import com.example.springfinalproject.model.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest) throws Exception;
    
    AppUserResponse register(AppUserRequest appUserRequest) throws MessagingException;

    void verify(String optCode);

    void resend(String email) throws MessagingException;

    boolean forget(String email, PasswordRequest passwordRequest) throws MessagingException;

    void reset(PasswordRequest passwordRequest);

    boolean verifyForgetPasswordOtp(String email, String otpCode, PasswordRequest passwordRequest);

    UserInfoWithRole userInfoWithRole();



}
