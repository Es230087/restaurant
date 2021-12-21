package com.example.restaurant.serviceTest;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.exception.FoodCategoryNotFoundException;
import com.example.restaurant.exception.IdNotFoundException;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTests {

    @Mock
    private MenuItemRepository menuItemRepo;
    @InjectMocks
    private MenuItemService menuItemService;

    //Testing the findAll method for success
    @Test
    public void findAll_withReturnValue_success() {
        //setup
        MenuItem menuItem = Mockito.mock(MenuItem.class);
        Iterable<MenuItem> expectedMenuItemIterable = List.of(menuItem);
        when(menuItemRepo.findAll()).thenReturn(expectedMenuItemIterable);

        //execute
        Iterable<MenuItem> actualMenuItemIterable = menuItemService.findAll();

        //assert
        assertEquals(menuItem, actualMenuItemIterable.iterator().next());

    }

    //Testing the findById method for success
    @Test
    public void findById_withValidIdAndMatchFound_success() {
        //setup
        int menuItemId = 1;
        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setMenuItemId(menuItemId);
        Optional<MenuItem> expectedMenuItemOptional = Optional.of(expectedMenuItem);
        when(menuItemRepo.findById(eq(menuItemId))).thenReturn(expectedMenuItemOptional);

        //execute
        Optional<MenuItem> actualMenuItemOptional =  menuItemService.findById(menuItemId);

        //assert
        assertEquals(menuItemId, actualMenuItemOptional.get().getMenuItemId());
    }

    //Testing the findById method for valid ID but no matches are found
    @Test
    public void findById_withValidIdAndMatchNotFound_success() {
        //setup
        int menuItemId = 1;
        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setMenuItemId(menuItemId);

        when(menuItemRepo.findById(menuItemId)).thenReturn(Optional.empty());

        //execute
        IdNotFoundException actualException = assertThrows(IdNotFoundException.class, () ->
                menuItemService.findById(menuItemId)
        );

        String expectedMessage = "ID not found";
        String actualMessage = actualException.getMessage();

        //assert
        assertEquals(expectedMessage, actualMessage);
    }

    //Testing the findByName method for success
    @Test
    public void findByName_withValidName_success() {
        //setup
        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setName("Salad");
        Iterable<MenuItem> expectedMenuItemIterable = List.of(expectedMenuItem);
        when(menuItemRepo.findByName(expectedMenuItem.getName())).thenReturn(expectedMenuItemIterable);

        //execute
        Iterable<MenuItem> actualMenuItemIterable = menuItemService.findByName(expectedMenuItem.getName());

        //assert
        assertEquals(actualMenuItemIterable.iterator().next().getName(), expectedMenuItemIterable.iterator().next().getName());


    }

    //Testing the findByFoodCategory method for success
    @Test
    public void findByFoodCategory_withValidFoodCategory_success() {
        //setup
        String searchItem = "Entree";
        MenuItem menuItem = new MenuItem();
        menuItem.setFoodCategory("Entree");
        Iterable<MenuItem> expectedSearchItemIterable = List.of(menuItem);
        when(menuItemRepo.findMenuItemByFoodCategory(searchItem)).thenReturn(expectedSearchItemIterable);

        //execute
        Iterable<MenuItem> actualMenuItemIterable = menuItemService.findByFoodCategory(searchItem);

        //assert
        assertEquals(searchItem, actualMenuItemIterable.iterator().next().getFoodCategory());
    }

    //FoodCategory not found exception valid foodCategory name but no results found
    @Test
    public void findByFoodCategory_withValidCategoryNameAndCategoryNotFound_throwsFoodCategoryNotFoundException() {
        //setup
        String categorySearchName = "Apple";
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Cheeseburger");
        menuItem.setFoodCategory("Entree");

        when(menuItemRepo.findMenuItemByFoodCategory(categorySearchName)).thenReturn(List.of());

        //execute
        FoodCategoryNotFoundException actualException = assertThrows(FoodCategoryNotFoundException.class, () ->
            menuItemService.findByFoodCategory(categorySearchName)
        );

        String expectedMessage = "Food category not found";
        String actualMessage = actualException.getMessage();

        //assert
        assertEquals(expectedMessage, actualMessage);
    }

    //Testing the findAllMenuItemsByPriceGreaterThan method for success
    @Test
    public void findAllMenuItemsByPriceGreaterThan_withValidPriceAndFoundMenuItem_success() {
        //setup
        BigDecimal searchPrice = BigDecimal.valueOf(5.00);
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1);
        menuItem.setName("Salad");
        menuItem.setFoodCategory("Entree");
        menuItem.setDescription("Leafy greens");
        menuItem.setPrice(BigDecimal.valueOf(8.00));
        menuItem.setInStock(true);

        List<MenuItem> listOfMenuItems = new ArrayList<>();
        listOfMenuItems.add(menuItem);
        when(menuItemRepo.findAllMenuItemsByPriceGreaterThan(searchPrice)).thenReturn(listOfMenuItems);

        //execute
        List<MenuItem> actualMenuItemList = menuItemService.findAllMenuItemsByPriceGreaterThan(searchPrice);

        //assert
        MenuItem actualMenuItem = actualMenuItemList.get(0);
        boolean actualMenuItemPriceIsGreaterThanSearchPrice = actualMenuItem.getPrice().compareTo(searchPrice) > 0;
        assertTrue(actualMenuItemPriceIsGreaterThanSearchPrice);
    }

    //Testing the findAllMenuItemsByPriceLessThan method for success
    @Test
    public void findAllMenuItemsByPriceLessThan_withValidPriceAndFoundMenuItem_success() {
        //setup
        BigDecimal searchPrice = BigDecimal.valueOf(5.00);
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1);
        menuItem.setName("Salad");
        menuItem.setFoodCategory("Entree");
        menuItem.setDescription("Leafy greens");
        menuItem.setPrice(BigDecimal.valueOf(4.00));
        menuItem.setInStock(true);

        List<MenuItem> listOfMenuItems = new ArrayList<>();
        listOfMenuItems.add(menuItem);
        when(menuItemRepo.findAllMenuItemsByPriceLessThan(searchPrice)).thenReturn(listOfMenuItems);

        //execute
        List<MenuItem> actualMenuItemList = menuItemService.findAllMenuItemsByPriceLessThan(searchPrice);

        //assert
        MenuItem actualMenuItem = actualMenuItemList.get(0);
        boolean actualMenuItemPriceIsLessThanSearchPrice = actualMenuItem.getPrice().compareTo(searchPrice) < 0;
        assertTrue(actualMenuItemPriceIsLessThanSearchPrice);
    }

    //Testing the findAllMenuItemsByInStockIsFalse method for success
    @Test
    public void findAllMenuItemsByInStockIsFalse_withValidBoolean_success() {
        //setup
        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setMenuItemId(1);
        expectedMenuItem.setName("Salad");
        expectedMenuItem.setFoodCategory("Entree");
        expectedMenuItem.setDescription("Leafy greens");
        expectedMenuItem.setPrice(BigDecimal.valueOf(8.00));
        expectedMenuItem.setInStock(false);

        List<MenuItem> expectedListOfMenuItems = new ArrayList<>();
        expectedListOfMenuItems.add(expectedMenuItem);
        when(menuItemRepo.findAllMenuItemsByInStockIsFalse()).thenReturn(expectedListOfMenuItems);

        //execute
        List<MenuItem> actualMenuItemList = menuItemService.findAllMenuItemsByInStockIsFalse();

        //assert
        assertSame(expectedMenuItem, actualMenuItemList.get(0));

    }
}
