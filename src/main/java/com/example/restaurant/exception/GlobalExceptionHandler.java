package com.example.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage clientErrorException(HttpClientErrorException ex, WebRequest request) {
        HttpStatus httpStatusCode = ex.getStatusCode();
        ErrorMessage message = new ErrorMessage(
                httpStatusCode.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(true));

        return message;
    }

    @ExceptionHandler(value = ShoppingCartNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Shopping cart not found")
    public ErrorMessage clientErrorExceptionShoppingCartNotFound(ShoppingCartNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(value = FoodCategoryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Food category not found")
    public ErrorMessage clientErrorExceptionFoodCategoryNotFound(FoodCategoryNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(value = IdNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "ID not found")
    public ErrorMessage clientErrorExceptionIdNotFound(IdNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }


}
