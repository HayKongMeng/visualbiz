package com.example.springfinalproject.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookNotification {
    private Integer notificationId;
    private Integer bookId;
}

