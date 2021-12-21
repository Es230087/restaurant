package com.example.restaurant.controllersTest;

import com.example.restaurant.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class})
@TestPropertySource(locations = "classpath:application.properties")
@WebAppConfiguration
public class MenuItemControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void findAllMenuItems_withoutNameQueryParam_success() throws Exception {
        String expectedMenuItemsJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 1,\n" +
                "        \"name\": \"Cheeseburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"A hamburger with cheese\",\n" +
                "        \"price\": 10.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 2,\n" +
                "        \"name\": \"Hamburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"Meat between two buns\",\n" +
                "        \"price\": 8.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 3,\n" +
                "        \"name\": \"Salad\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"Leafy greens\",\n" +
                "        \"price\": 8.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 4,\n" +
                "        \"name\": \"French Fries\",\n" +
                "        \"foodCategory\": \"Side\",\n" +
                "        \"description\": \"Cut and fried potatoes\",\n" +
                "        \"price\": 3.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 5,\n" +
                "        \"name\": \"Onion Rings\",\n" +
                "        \"foodCategory\": \"Side\",\n" +
                "        \"description\": \"Cut and fried onions\",\n" +
                "        \"price\": 3.00,\n" +
                "        \"inStock\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 6,\n" +
                "        \"name\": \"Vanilla Ice Cream\",\n" +
                "        \"foodCategory\": \"Dessert\",\n" +
                "        \"description\": \"Ice cream with vanilla flavoring\",\n" +
                "        \"price\": 5.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 7,\n" +
                "        \"name\": \"Chocolate Ice Cream\",\n" +
                "        \"foodCategory\": \"Dessert\",\n" +
                "        \"description\": \"Ice cream with chocolate flavoring\",\n" +
                "        \"price\": 5.00,\n" +
                "        \"inStock\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 8,\n" +
                "        \"name\": \"Water\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Water\",\n" +
                "        \"price\": 0.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 9,\n" +
                "        \"name\": \"Soda\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Carbonated water with flavoring\",\n" +
                "        \"price\": 2.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 10,\n" +
                "        \"name\": \"Ice Tea\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Water with tea leaves\",\n" +
                "        \"price\": 2.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemsJson));

    }

    @Test
    public void findAllMenuItems_withValidMenuItemNameQueryParam_success() throws Exception {

        String expectedMenuItemJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 3,\n" +
                "        \"name\": \"Salad\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"Leafy greens\",\n" +
                "        \"price\": 8.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/menu").queryParam("name", "Salad"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemJson));

    }


    @Test
    public void findByFoodCategory_withValidFoodCategory_success() throws Exception {

        String expectedMenuItemsJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 1,\n" +
                "        \"name\": \"Cheeseburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"A hamburger with cheese\",\n" +
                "        \"price\": 10.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 2,\n" +
                "        \"name\": \"Hamburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"Meat between two buns\",\n" +
                "        \"price\": 8.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 3,\n" +
                "        \"name\": \"Salad\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"Leafy greens\",\n" +
                "        \"price\": 8.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";


        this.mockMvc.perform(get("/menu/items/{foodCategory}", "Entree")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemsJson));
    }

    @Test
    public void findByName_withValidName_success() throws Exception {

        String expectedMenuItemJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 1,\n" +
                "        \"name\": \"Cheeseburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"A hamburger with cheese\",\n" +
                "        \"price\": 10.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/{name}", "Cheeseburger")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemJson));
    }

    @Test
    public void findAllMenuItemsByPriceLessThan_withValidPrice_success() throws Exception {

        String expectedMenuItemsJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 8,\n" +
                "        \"name\": \"Water\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Water\",\n" +
                "        \"price\": 0.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 9,\n" +
                "        \"name\": \"Soda\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Carbonated water with flavoring\",\n" +
                "        \"price\": 2.00,\n" +
                "        \"inStock\": true\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 10,\n" +
                "        \"name\": \"Ice Tea\",\n" +
                "        \"foodCategory\": \"Beverage\",\n" +
                "        \"description\": \"Water with tea leaves\",\n" +
                "        \"price\": 2.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/searchPrice").queryParam("searchPrice", "3.00")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemsJson));

    }

    @Test
    public void findAllMenuItemsByPriceGreaterThan_withValidPrice_success() throws Exception {

        String expectedMenuItemJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 1,\n" +
                "        \"name\": \"Cheeseburger\",\n" +
                "        \"foodCategory\": \"Entree\",\n" +
                "        \"description\": \"A hamburger with cheese\",\n" +
                "        \"price\": 10.00,\n" +
                "        \"inStock\": true\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/price").queryParam("searchPrice", "9.00")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemJson));
    }

    @Test
    public void findAllMenuItemsNotInStock_success() throws Exception {

        String expectedMenuItemsJson = "[\n" +
                "    {\n" +
                "        \"menuItemId\": 5,\n" +
                "        \"name\": \"Onion Rings\",\n" +
                "        \"foodCategory\": \"Side\",\n" +
                "        \"description\": \"Cut and fried onions\",\n" +
                "        \"price\": 3.00,\n" +
                "        \"inStock\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"menuItemId\": 7,\n" +
                "        \"name\": \"Chocolate Ice Cream\",\n" +
                "        \"foodCategory\": \"Dessert\",\n" +
                "        \"description\": \"Ice cream with chocolate flavoring\",\n" +
                "        \"price\": 5.00,\n" +
                "        \"inStock\": false\n" +
                "    }\n" +
                "]";

        this.mockMvc.perform(get("/notInStock")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedMenuItemsJson));
    }
}
