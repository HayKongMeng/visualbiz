package com.example.springfinalproject.controller.userController;

import com.example.springfinalproject.model.request.UserRequest;
import com.example.springfinalproject.model.response.UserResponse;
import com.example.springfinalproject.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin

public class UserController {
    private final UserService userService;
    @PutMapping("/edit")
    @Operation(summary = "Edit user information")
    public ResponseEntity<?> editUser(@RequestBody UserRequest userRequest) {
        UserRequest userRequest1 = userService.editUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userRequest1);
    }
    @GetMapping
    @Operation(summary = "Get user information")
    public ResponseEntity<?> getUserInformation() {
        UserResponse userResponse = userService.getUserInformation();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
