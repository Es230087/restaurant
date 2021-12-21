package com.example.restaurant.service;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.exception.FoodCategoryNotFoundException;
import com.example.restaurant.exception.IdNotFoundException;
import com.example.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuRepo;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuRepo = menuItemRepository;
    }

    public Iterable<MenuItem> findAll(){

        return menuRepo.findAll();
    }

    public Optional<MenuItem> findById(int id) {
        Optional<MenuItem> searchItem = this.menuRepo.findById(id);
        if(searchItem.isEmpty()) {
            throw new IdNotFoundException("ID not found");
        }

        return searchItem;
    }

    public Iterable<MenuItem> findByName(String menuItemName) {
        Iterable<MenuItem> searchItem = this.menuRepo.findByName(menuItemName);

        return searchItem;
    }

    public List<MenuItem> findByFoodCategory(String categoryName) {
        List<MenuItem> listOfFoodCategory = (List<MenuItem>) this.menuRepo.findMenuItemByFoodCategory(categoryName);
        if(listOfFoodCategory.isEmpty() == true) {
            throw new FoodCategoryNotFoundException("Food category not found");
        }

        return listOfFoodCategory;
    }

    public List<MenuItem> findAllMenuItemsByPriceGreaterThan(BigDecimal price) {
        List<MenuItem> greaterThanList = this.menuRepo.findAllMenuItemsByPriceGreaterThan(price);

        return greaterThanList;
    }

    public List<MenuItem> findAllMenuItemsByPriceLessThan(BigDecimal searchPrice) {
        List<MenuItem> lessThanList = this.menuRepo.findAllMenuItemsByPriceLessThan(searchPrice);

        return lessThanList;
    }

    public List<MenuItem> findAllMenuItemsByInStockIsFalse() {
        List<MenuItem> notInStockList = this.menuRepo.findAllMenuItemsByInStockIsFalse();

        return notInStockList;
    }
}
