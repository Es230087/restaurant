package com.example.restaurant.service;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.ShoppingCart;
import com.example.restaurant.exception.ShoppingCartNotFoundException;
import com.example.restaurant.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepo;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepo = shoppingCartRepository;
    }

    public Iterable<ShoppingCart> findAll(){
        return shoppingCartRepo.findAll();
    }

    public Optional<ShoppingCart> findById(int id) {
        Optional<ShoppingCart> userCart = shoppingCartRepo.findById(id);
        if(userCart.isEmpty()) {
            throw new ShoppingCartNotFoundException("Shopping cart not found");
        }

        return userCart;
    }

    public ShoppingCart save(ShoppingCart tempCart) {
        ShoppingCart savedShoppingCart = shoppingCartRepo.save(tempCart);

        return savedShoppingCart;
    }

    public void updateShoppingCartTotal(ShoppingCart shoppingCart) {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.addAll(shoppingCart.getMenuItems());
        double shoppingCartTotal = 0.00;

        Optional<ShoppingCart> shoppingCartOptional = Optional.of(shoppingCart);
        if (shoppingCartOptional.isEmpty()) {
            throw new ShoppingCartNotFoundException("Shopping cart not found");
        }

        for (MenuItem menuItem : menuItemList) {
            shoppingCartTotal = shoppingCartTotal + menuItem.getPrice().doubleValue();
        }

        shoppingCart.setShoppingCartTotal(BigDecimal.valueOf(shoppingCartTotal));
    }
}
