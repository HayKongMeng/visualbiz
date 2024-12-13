package com.example.springfinalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyCreateException.class)
    public ProblemDetail handleExistExceptionCustom (AlreadyCreateException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,  ex.getMessage());
        problemDetail.setTitle(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestExceptionCustom (BadRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,  ex.getMessage());
        problemDetail.setTitle(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundExceptionCustom (NotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,  ex.getMessage());
        problemDetail.setTitle("Not Found");
        return problemDetail;
    }


    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleFileUploadException(FileUploadException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("File Upload Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (var parameterError: ex.getAllValidationResults()){
            String parameterName = parameterError.getMethodParameter().getParameterName();
            for (var error : parameterError.getResolvableErrors()){
                errors.put(parameterName, error.getDefaultMessage());
            }
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("Bad request 400");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (var fieldError: ex.getBindingResult().getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }




}
