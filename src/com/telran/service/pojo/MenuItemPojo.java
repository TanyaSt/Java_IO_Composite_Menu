package com.telran.service.pojo;

import java.util.Objects;
import java.util.Set;

public class MenuItemPojo {
    private final String name;
    private final double price;
    private final String category;
    private final Set<IngredientPojo> ingredients;

    private MenuItemPojo(String name, double price, String category, Set<IngredientPojo> ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public static MenuItemPojo of(String name, double price, String category, Set<IngredientPojo> ingredients){
        return new MenuItemPojo(Objects.requireNonNull(name),price,Objects.requireNonNull(category),Objects.requireNonNull(ingredients));
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public Set<IngredientPojo> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemPojo menuItemPojo = (MenuItemPojo) o;
        return name.equals(menuItemPojo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MenuPojo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
