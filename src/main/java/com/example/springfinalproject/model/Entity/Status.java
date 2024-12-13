package com.example.springfinalproject.model.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Status {
    private Integer statusId;
    private String statusType;
}
