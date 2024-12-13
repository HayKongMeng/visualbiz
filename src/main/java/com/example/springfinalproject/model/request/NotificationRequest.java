package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationRequest {
    private String notificationType;
    private String notificationMessage;
    private LocalDateTime notificationDate;
    private boolean isRead = false;
}
