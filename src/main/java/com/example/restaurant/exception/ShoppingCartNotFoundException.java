package com.example.restaurant.exception;

public class ShoppingCartNotFoundException extends RuntimeException{

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}
