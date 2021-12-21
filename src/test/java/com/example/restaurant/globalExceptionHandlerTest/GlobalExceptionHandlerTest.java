package com.example.restaurant.globalExceptionHandlerTest;

import com.example.restaurant.exception.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    //Testing the ClientErrorException class
    @Test
    public void clientErrorException_withClientErrorException_success() {

        //setup
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Test message");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(true)).thenReturn("Test string");

        //execute
        ErrorMessage actualErrorMessage = globalExceptionHandler.clientErrorException(httpClientErrorException, webRequest);

        //assert
        assertEquals(httpClientErrorException.getStatusCode().value(), actualErrorMessage.getStatusCode());
        assertEquals(httpClientErrorException.getMessage(), actualErrorMessage.getMessage());
        assertEquals(webRequest.getDescription(true), actualErrorMessage.getDescription());

    }

    //Testing the Client Error Exception Shopping Cart Not Found
    @Test
    public void clientErrorExceptionShoppingCartNotFound_withClientErrorException_success() {

        //setup
        ShoppingCartNotFoundException shoppingCartNotFoundException = new ShoppingCartNotFoundException("Test message");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("Test string");

        //execute
        ErrorMessage actualErrorMessage = globalExceptionHandler.clientErrorExceptionShoppingCartNotFound(shoppingCartNotFoundException, webRequest);

        //assert
        int expectedStatusCodeValue = HttpStatus.NOT_FOUND.value();
        assertEquals(expectedStatusCodeValue, actualErrorMessage.getStatusCode());
        assertEquals(shoppingCartNotFoundException.getMessage(), actualErrorMessage.getMessage());
        assertEquals(webRequest.getDescription(false), actualErrorMessage.getDescription());

    }

    //Testing the Client Error Exception Food Category Not Found
    @Test
    public void clientErrorExceptionFoodCategoryNotFound_withClientErrorException_success() {
        FoodCategoryNotFoundException foodCategoryNotFoundException = new FoodCategoryNotFoundException("Test message");
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("Test string");

        //execute
        ErrorMessage actualErrorMessage = globalExceptionHandler.clientErrorExceptionFoodCategoryNotFound(foodCategoryNotFoundException, webRequest);

        //assert
        int expectedStatusCodeValue = HttpStatus.NOT_FOUND.value();
        assertEquals(expectedStatusCodeValue, actualErrorMessage.getStatusCode());
        assertEquals(foodCategoryNotFoundException.getMessage(), actualErrorMessage.getMessage());
        assertEquals(webRequest.getDescription(false), actualErrorMessage.getDescription());
    }

    //Testing the Client Error Exception ID Not Found
    @Test
    public void clientErrorExceptionIdNotFound_withClientErrorException_success() {
        IdNotFoundException idNotFoundException = new IdNotFoundException("Test message");
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("Test string");

        //execute
        ErrorMessage actualErrorMessage = globalExceptionHandler.clientErrorExceptionIdNotFound(idNotFoundException, webRequest);

        //assert
        int expectedStatusCodeValue = HttpStatus.NOT_FOUND.value();
        assertEquals(expectedStatusCodeValue, actualErrorMessage.getStatusCode());
        assertEquals(idNotFoundException.getMessage(), actualErrorMessage.getMessage());
        assertEquals(webRequest.getDescription(false), actualErrorMessage.getDescription());
    }

}
