package com.example.springfinalproject.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponse<T> {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private HttpStatus httpStatus;
    private Timestamp timestamp;
}
