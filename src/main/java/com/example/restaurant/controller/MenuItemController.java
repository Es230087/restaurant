package com.example.restaurant.controller;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.service.MenuItemService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {

        this.menuItemService = menuItemService;
    }

    //Find all items on the menu or the MenuItemRepo
    @GetMapping("/menu")
    public Iterable<MenuItem> findAll(@RequestParam(required = false) String name) {
        Iterable<MenuItem> menuItem;

        if (name != null) {
           menuItem = this.menuItemService.findByName(name);
        } else {
            menuItem = this.menuItemService.findAll();
        }


        return menuItem;
    }

    //Finds all menuItems by foodCategory
    @GetMapping("/menu/items/{foodCategory}")
    public List<MenuItem> findByFoodCategory(@PathVariable String foodCategory) {
        List<MenuItem> menuItemList = this.menuItemService.findByFoodCategory(foodCategory);

        return menuItemList;
    }

    //Finds all menuItems by the name of the menuItem
    @GetMapping("/{name}")
    public Iterable<MenuItem> findByName(@PathVariable String name) {
        Iterable<MenuItem> searchItem = this.menuItemService.findByName(name);
        return searchItem;
    }

    //Finds all menuItems less than the given price
    @GetMapping("/searchPrice")
    public List<MenuItem> findAllMenuItemsByPriceLessThan(@RequestParam(required = true) BigDecimal searchPrice) {
        List<MenuItem> searchResults = this.menuItemService.findAllMenuItemsByPriceLessThan(searchPrice);
        return searchResults;
    }

    //Find all menuItems greater than the given price
    @GetMapping("/price")
    public List<MenuItem> findAllMenuItemsByPriceGreaterThan(@RequestParam(required = true) BigDecimal searchPrice) {
        List<MenuItem> searchResults = this.menuItemService.findAllMenuItemsByPriceGreaterThan(searchPrice);
        return searchResults;
    }


    //Find all items that are not in stock
    @GetMapping("/notInStock")
    public List<MenuItem> findAllMenuItemsNotInStock() {
        List<MenuItem> searchResults = this.menuItemService.findAllMenuItemsByInStockIsFalse();
        return searchResults;
    }
}
