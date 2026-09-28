package com.example.restaurantcustomerservice.model;

import java.util.*;

public class MenuData {
    private static final List<MenuItem> starters = new ArrayList<>();
    private static final List<MenuItem> mains = new ArrayList<>();
    private static final List<MenuItem> desserts = new ArrayList<>();
    private static final List<MenuItem> drinks = new ArrayList<>();

    static {
        starters.add(new MenuItem("Bruschetta", 4.5));
        starters.add(new MenuItem("Soup", 3.0));
        starters.add(new MenuItem("Spring Rolls", 4.0));
        starters.add(new MenuItem("Garlic Bread", 3.5));
        starters.add(new MenuItem("Fish Chips", 5.0));

        mains.add(new MenuItem("Spaghetti", 8.0));
        mains.add(new MenuItem("Carbonara", 8.5));
        mains.add(new MenuItem("Burger Steak", 7.5));
        mains.add(new MenuItem("Grilled Chicken", 9.0));
        mains.add(new MenuItem("Veg Stir Fry", 6.5));

        desserts.add(new MenuItem("Cheesecake", 4.0));
        desserts.add(new MenuItem("Chocolate Lava Cake", 5.0));
        desserts.add(new MenuItem("Tiramisu", 4.8));
        desserts.add(new MenuItem("Fruit Salad", 3.0));
        desserts.add(new MenuItem("Ice Cream", 2.5));

        drinks.add(new MenuItem("Coke", 1.5));
        drinks.add(new MenuItem("Tea", 1.2));
        drinks.add(new MenuItem("Coffee", 1.8));
        drinks.add(new MenuItem("Cold Coffee", 2.0));
        drinks.add(new MenuItem("Orange Juice", 2.5));
    }

    public static List<MenuItem> getItems(String category) {
        return switch (category) {
            case "Starters" -> starters;
            case "Mains" -> mains;
            case "Desserts" -> desserts;
            case "Drinks" -> drinks;
            default -> new ArrayList<>();
        };
    }

    public static void addItem(String category, String name, double price) {
        getItems(category).add(new MenuItem(name, price));
    }
}  
