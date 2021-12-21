package com.example.restaurant.repository;

import com.example.restaurant.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {

}
