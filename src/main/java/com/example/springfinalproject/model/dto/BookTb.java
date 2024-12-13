package com.example.springfinalproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookTb {
    Integer bookId;
    Integer statusId;
    Integer userId;
    Integer shopId;
}
