package com.example.restaurant.controller;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.ShoppingCart;
import com.example.restaurant.exception.ShoppingCartNotFoundException;
import com.example.restaurant.service.MenuItemService;
import com.example.restaurant.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final MenuItemService menuItemService;
    //add menu item repository bean

    //Constructor injection
    public ShoppingCartController(ShoppingCartService shoppingCartService, MenuItemService menuItemService) {
        this.shoppingCartService = shoppingCartService;
        this.menuItemService = menuItemService;
    }

    //Gets all shopping carts from the shoppingCartRepo
    @GetMapping("/shoppingcart")
    public Iterable<ShoppingCart> getAll() {
        return shoppingCartService.findAll();
    }

    //Finds a specific shopping cart
    @GetMapping("/shoppingcart/{shoppingCartId}")
    public Optional<ShoppingCart> getShoppingCart(@PathVariable int shoppingCartId) {
        Optional<ShoppingCart> cartToSearchFor = this.shoppingCartService.findById(shoppingCartId);

        if (cartToSearchFor.isEmpty()) {
            throw new ShoppingCartNotFoundException("Shopping cart not found");
        }

        return shoppingCartService.findById(shoppingCartId);
    }

    //Creates shoppingCart and adds a menuItem to newly created shoppingCart
    @PostMapping(value = "/shoppingcart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCart createShoppingCart(@RequestBody MenuItem menuItem) {
        // check if menu item request body is valid, that it exists
        // if not valid, return bad request
        Optional<MenuItem> searchItem = this.menuItemService.findById(menuItem.getMenuItemId());

        if (searchItem.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        } else {
            //create a new shopping cart object
            //add menu item to that shopping cart object
            //save shopping cart object to the shopping cart repository
            ShoppingCart tempCart = new ShoppingCart();
            tempCart.setCreatedDate(LocalDate.now());
            tempCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));
            List<MenuItem> menuItems = new ArrayList<>();
            menuItems.add(searchItem.get());
            tempCart.setMenuItems(menuItems);
            shoppingCartService.updateShoppingCartTotal(tempCart);
            ShoppingCart savedShoppingCart = this.shoppingCartService.save(tempCart);

            return savedShoppingCart;
        }

    }

    //Adds a menuItem to the shopping cart
    @PutMapping("/shoppingcart/{shoppingCartId}/menuitem")
    public ShoppingCart addMenuItem(@PathVariable Integer shoppingCartId, @RequestBody MenuItem menuItem) {
        //Verify the shopping cart exist
        Optional<ShoppingCart> userShoppingCart = shoppingCartService.findById(shoppingCartId);
        if (userShoppingCart.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        //Verify the menuItem exist
        Optional<MenuItem> menuItemOptional = menuItemService.findById(menuItem.getMenuItemId());
        if (menuItemOptional.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        //Adding menuItem to existing shopping cart
        ShoppingCart tempCart = userShoppingCart.get();
        tempCart.getMenuItems().add(menuItemOptional.get());
        shoppingCartService.updateShoppingCartTotal(tempCart);
        ShoppingCart savedShoppingCart = this.shoppingCartService.save(tempCart);

        return savedShoppingCart;

    }

    //Deletes a menuItem from a shoppingCart
    @DeleteMapping("/shoppingcart/{shoppingCartId}/menuitem/{menuItemId}")
    public ResponseEntity deleteMenuItem(@PathVariable Integer shoppingCartId, @PathVariable Integer menuItemId) {
        //find the shopping cart
        //iterate thru to find item
        //perform delete operation
        //save new state of shopping cart
        //return Http 204 No Content

        Optional<ShoppingCart> userCart = shoppingCartService.findById(shoppingCartId);
        if (userCart.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        //retrieve menuItemId from menuItemRepository
        //check to make sure it's valid
        Optional<MenuItem> menuItemOptional = menuItemService.findById(menuItemId);
        if (menuItemOptional.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        MenuItem menuItem = menuItemOptional.get();
        boolean contains = userCart.get().getMenuItems().contains(menuItem);
        if (contains) {
            ShoppingCart shoppingCart = userCart.get();
            shoppingCart.getMenuItems().remove(menuItem);
            shoppingCartService.updateShoppingCartTotal(shoppingCart);
            shoppingCartService.save(shoppingCart);
            return ResponseEntity.noContent().build();
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }
}
