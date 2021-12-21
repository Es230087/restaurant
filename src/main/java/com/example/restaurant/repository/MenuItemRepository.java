package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
    Iterable<MenuItem> findByName(String name);
    Iterable<MenuItem> findMenuItemByFoodCategory(String foodCategory);
    List<MenuItem> findAllMenuItemsByPriceGreaterThan(BigDecimal price);
    List<MenuItem> findAllMenuItemsByPriceLessThan(BigDecimal searchPrice);
    List<MenuItem> findAllMenuItemsByInStockIsFalse();
}
