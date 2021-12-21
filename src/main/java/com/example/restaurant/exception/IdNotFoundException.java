package com.example.restaurant.exception;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message) {
        super(message);
    }
}
