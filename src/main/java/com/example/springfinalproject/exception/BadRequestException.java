package com.example.springfinalproject.exception;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
