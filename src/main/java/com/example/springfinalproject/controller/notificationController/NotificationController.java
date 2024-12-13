package com.example.springfinalproject.controller.notificationController;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.request.NotificationRequest;
import com.example.springfinalproject.model.response.NotificationResponse;
import com.example.springfinalproject.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@CrossOrigin
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping()
    @Operation(summary = "Get All Notification")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotification();
    }

    @GetMapping("/id")
    public Notification getNotificationById(@Positive Integer id){
        return notificationService.findbyid(id);
    }
    @PostMapping
    public ResponseEntity<NotificationResponse<Notification>> addNewNotifi(@RequestBody @Valid NotificationRequest notificationRequest){
        Integer store = notificationService.addNewNotification(notificationRequest);
        NotificationResponse<Notification> response = NotificationResponse.<Notification>builder()
                .message("heooe")
                .payload(notificationService.findbyid(store))
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse<Notification>> updateNotification(@RequestBody @Valid NotificationRequest notificationRequest,@PathVariable("id") @Positive Integer id){
        Integer updateNotification = notificationService.updateNotification(notificationRequest,id);
        NotificationResponse<Notification> response = NotificationResponse.<Notification>builder()
                .message("heooe")
                .payload(notificationService.findbyid(updateNotification))
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationResponse<Notification>> deleteNotification(@PathVariable("id") @Positive Integer id){
        boolean deleteNotification = notificationService.deleteNotification(id);
        NotificationResponse<Notification> response = NotificationResponse.<Notification>builder()
                .message("heooe")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }
}
