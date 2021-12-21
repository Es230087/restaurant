package com.example.restaurant.controllersTest;

import com.example.restaurant.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class})
@TestPropertySource(locations = "classpath:application.properties")
@WebAppConfiguration
public class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    String menuItem = "{\n" +
            "    \"menuItemId\": 3,\n" +
            "    \"name\": \"Salad\",\n" +
            "    \"foodCategory\": \"Entree\",\n" +
            "    \"description\": \"Leafy greens\",\n" +
            "    \"price\": 8.00,\n" +
            "    \"inStock\": true\n" +
            "}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem));
    }

    @Test
    public void createShoppingCart_withValidMenuItem_success() throws Exception {

        mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem));

        String expectedShoppingCart = "{\n" +
                "    \"shoppingCartId\": 3,\n" +
                "    \"createdDate\": \"2021-12-20\",\n" +
                "    \"shoppingCartTotal\": 8.0,\n" +
                "    \"menuItems\": [\n" +
                "        {\n" +
                "            \"menuItemId\": 3,\n" +
                "            \"name\": \"Salad\",\n" +
                "            \"foodCategory\": \"Entree\",\n" +
                "            \"description\": \"Leafy greens\",\n" +
                "            \"price\": 8.00,\n" +
                "            \"inStock\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        this.mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedShoppingCart));
    }

    @Test
    public void findAllShoppingCarts_success() throws Exception {
        mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem));

        String expectedShoppingCart = "[\n" +
                "    {\n" +
                "        \"shoppingCartId\": 1,\n" +
                "        \"createdDate\": \"2021-12-20\",\n" +
                "        \"shoppingCartTotal\": 8.00,\n" +
                "        \"menuItems\": [\n" +
                "            {\n" +
                "                \"menuItemId\": 3,\n" +
                "                \"name\": \"Salad\",\n" +
                "                \"foodCategory\": \"Entree\",\n" +
                "                \"description\": \"Leafy greens\",\n" +
                "                \"price\": 8.00,\n" +
                "                \"inStock\": true\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"shoppingCartId\": 2,\n" +
                "        \"createdDate\": \"2021-12-20\",\n" +
                "        \"shoppingCartTotal\": 8.00,\n" +
                "        \"menuItems\": [\n" +
                "            {\n" +
                "                \"menuItemId\": 3,\n" +
                "                \"name\": \"Salad\",\n" +
                "                \"foodCategory\": \"Entree\",\n" +
                "                \"description\": \"Leafy greens\",\n" +
                "                \"price\": 8.00,\n" +
                "                \"inStock\": true\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";


        this.mockMvc.perform(get("/shoppingcart")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedShoppingCart));
    }

    @Test
    public void findShoppingCart_withValidId_success() throws Exception {
        mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem));

        String expectedShoppingCart = "{\n" +
                "    \"shoppingCartId\": 1,\n" +
                "    \"createdDate\": \"2021-12-14\",\n" +
                "    \"shoppingCartTotal\": 8.0,\n" +
                "    \"menuItems\": [\n" +
                "        {\n" +
                "            \"menuItemId\": 3,\n" +
                "            \"name\": \"Salad\",\n" +
                "            \"foodCategory\": \"Entree\",\n" +
                "            \"description\": \"Leafy greens\",\n" +
                "            \"price\": 8.00,\n" +
                "            \"inStock\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        this.mockMvc.perform(get("/shoppingcart/{shoppingCarId}", "1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedShoppingCart));
    }


    @Test
    public void addMenuItem_withValidShoppingCartIdAndValidMenuItem_success() throws Exception {
        mockMvc.perform(post("/shoppingcart").contentType(MediaType.APPLICATION_JSON).content(menuItem));

        String expectedShoppingCart = "{\n" +
                "    \"shoppingCartId\": 1,\n" +
                "    \"createdDate\": \"2021-12-14\",\n" +
                "    \"shoppingCartTotal\": 16.0,\n" +
                "    \"menuItems\": [\n" +
                "        {\n" +
                "            \"menuItemId\": 3,\n" +
                "            \"name\": \"Salad\",\n" +
                "            \"foodCategory\": \"Entree\",\n" +
                "            \"description\": \"Leafy greens\",\n" +
                "            \"price\": 8.00,\n" +
                "            \"inStock\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuItemId\": 3,\n" +
                "            \"name\": \"Salad\",\n" +
                "            \"foodCategory\": \"Entree\",\n" +
                "            \"description\": \"Leafy greens\",\n" +
                "            \"price\": 8.00,\n" +
                "            \"inStock\": true\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        this.mockMvc.perform(put("/shoppingcart/{shoppingCartId}/menuitem", "1")
                .contentType(MediaType.APPLICATION_JSON).content(menuItem))
                .andExpect(content().json(expectedShoppingCart));
    }

    @Test
    public void deleteMenuItem_withValidShoppingCartIdAndValidMenuItem_success() throws Exception {

        this.mockMvc.perform(delete("/shoppingcart/{shoppingCartId}/menuitem/{menuItemId}", "1", "3"))
                .andExpect(status().isNoContent());
    }


}
