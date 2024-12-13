package com.example.springfinalproject.exception;

public class AlreadyCreateException extends RuntimeException {
    public AlreadyCreateException(String message) {
        super(message);
    }
}
