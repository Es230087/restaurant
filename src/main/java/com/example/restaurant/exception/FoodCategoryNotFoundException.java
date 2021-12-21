package com.example.restaurant.exception;

public class FoodCategoryNotFoundException extends RuntimeException{

    public FoodCategoryNotFoundException(String message) {
        super(message);
    }
}
