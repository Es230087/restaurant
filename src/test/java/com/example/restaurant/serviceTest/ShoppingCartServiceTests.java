package com.example.restaurant.serviceTest;

import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.ShoppingCart;
import com.example.restaurant.exception.ShoppingCartNotFoundException;
import com.example.restaurant.repository.ShoppingCartRepository;
import com.example.restaurant.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTests {

    @Mock
    private ShoppingCartRepository shoppingCartRepo;
    @InjectMocks
    private ShoppingCartService shoppingCartService;


    //Testing the findAll method
    @Test
    public void FindAll_withValidShoppingCart_success() {
        //setup
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setShoppingCartId(1);
        expectedShoppingCart.setCreatedDate(LocalDate.now());
        expectedShoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));

        MenuItem expectedMenuItem = new MenuItem();
        expectedMenuItem.setName("Salad");
        expectedMenuItem.setFoodCategory("Entree");
        expectedMenuItem.setDescription("Leafy greens");
        expectedMenuItem.setPrice(BigDecimal.valueOf(8.00));
        expectedMenuItem.setInStock(true);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(expectedMenuItem);
        expectedShoppingCart.setMenuItems(menuItems);

        Iterable<ShoppingCart> shoppingCartIterable = Arrays.asList(expectedShoppingCart);
        when(shoppingCartRepo.findAll()).thenReturn(shoppingCartIterable);

        //execute
        Iterable<ShoppingCart> actualShoppingCart = shoppingCartService.findAll();

        //assert
        assertEquals(shoppingCartIterable, actualShoppingCart);


    }

    //Testing the findById method
    @Test
    public void findById_withValidId_success() {
        //setup
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setShoppingCartId(1);
        expectedShoppingCart.setCreatedDate(LocalDate.now());
        expectedShoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));

        Optional<ShoppingCart> shoppingCartOptional = Optional.of(expectedShoppingCart);
        when(shoppingCartRepo.findById(expectedShoppingCart.getShoppingCartId())).thenReturn(shoppingCartOptional);

        //execute
        Optional<ShoppingCart> actualShoppingCartOptional = shoppingCartService.findById(expectedShoppingCart.getShoppingCartId());

        //assert
        assertEquals(expectedShoppingCart.getShoppingCartId(), actualShoppingCartOptional.get().getShoppingCartId());
    }

    //ShoppingCart not found exception valid ID but no results found
    @Test
    public void findById_withValidIdAndNoMatch_throwsShoppingCartNotFoundException() {
        //setup
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShoppingCartId(1);
        shoppingCart.setCreatedDate(LocalDate.now());
        shoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));

        when(shoppingCartRepo.findById(shoppingCart.getShoppingCartId())).thenReturn(Optional.empty());

        //execute
        ShoppingCartNotFoundException actualException = assertThrows(ShoppingCartNotFoundException.class, () ->
                shoppingCartService.findById(shoppingCart.getShoppingCartId())
        );

        String expectedMessage = "Shopping cart not found";
        String actualMessage = actualException.getMessage();

        //assert
        assertEquals(expectedMessage, actualMessage);

    }

    //Testing the save function
    @Test
    public void save_withValidShoppingCart_success() {
        //setup
        ShoppingCart expectedShoppingCart = new ShoppingCart();
        expectedShoppingCart.setShoppingCartId(1);
        expectedShoppingCart.setCreatedDate(LocalDate.now());
        expectedShoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));

        ShoppingCart unSavedShoppingCart = new ShoppingCart();
        unSavedShoppingCart.setCreatedDate(LocalDate.now());
        unSavedShoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));

        when(shoppingCartRepo.save(eq(unSavedShoppingCart))).thenReturn(expectedShoppingCart);

        //execute
        ShoppingCart actualShoppingCart = shoppingCartService.save(unSavedShoppingCart);

        //assert
        assertEquals(expectedShoppingCart, actualShoppingCart);
    }

    //Testing the updateShoppingCartTotal method
    @Test
    public void updateShoppingCartTotal_withValidShoppingCart_success() {
        //setup
        ShoppingCart updatedShoppingCart = new ShoppingCart();
        updatedShoppingCart.setShoppingCartId(1);
        updatedShoppingCart.setCreatedDate(LocalDate.now());
        updatedShoppingCart.setShoppingCartTotal(BigDecimal.valueOf(0.00));
        updatedShoppingCart.setMenuItems(new ArrayList<>());

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1);
        menuItem.setName("Salad");
        menuItem.setFoodCategory("Entree");
        menuItem.setDescription("Leafy greens");
        menuItem.setPrice(BigDecimal.valueOf(8.00));
        menuItem.setInStock(true);

        double shoppingCartTotalBeforeUpdate = updatedShoppingCart.getShoppingCartTotal().doubleValue();

        updatedShoppingCart.getMenuItems().add(menuItem);

        //execute
        shoppingCartService.updateShoppingCartTotal(updatedShoppingCart);

        double shoppingCartTotalAfterUpdate = updatedShoppingCart.getShoppingCartTotal().doubleValue();

        //assert
        assertNotEquals(shoppingCartTotalBeforeUpdate, shoppingCartTotalAfterUpdate);
    }


}
