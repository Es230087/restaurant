package com.example.restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "MENU_ITEMS")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer menuItemId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FOOD_CATEGORY")
    private String foodCategory;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "IN_STOCK")
    private Boolean inStock;

    @JsonIgnore
    @ManyToMany(mappedBy = "menuItems")
    private List<ShoppingCart> shoppingCart;

}
