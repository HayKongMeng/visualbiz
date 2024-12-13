package com.example.springfinalproject.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImportProductResponse <T>{
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private HttpStatus httpStatus;
}
