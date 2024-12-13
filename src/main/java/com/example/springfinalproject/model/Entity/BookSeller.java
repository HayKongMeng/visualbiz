package com.example.springfinalproject.model.Entity;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookSeller {
    private Integer bookId;
    private String username;
    private String profileImg;
    private String serviceName;
    private LocalDateTime startDate;
    private String status;

}
