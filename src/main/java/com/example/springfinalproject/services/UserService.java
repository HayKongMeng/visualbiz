package com.example.springfinalproject.services;

import com.example.springfinalproject.model.request.UserRequest;
import com.example.springfinalproject.model.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRequest editUser(UserRequest userRequest );

    UserResponse getUserInformation();

    UserResponse getUserInformationWithId(Integer userId);

    UserResponse loginWithGoogle(UserRequest userRequest);

}
