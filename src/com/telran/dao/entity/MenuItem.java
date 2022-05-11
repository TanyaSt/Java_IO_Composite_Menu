package com.telran.dao.entity;

import java.util.Objects;
import java.util.Set;

public class MenuItem {
    private final String name;
    private final double price;
    private final String category;
    private final Set<Ingredient> ingredients;

    public MenuItem(String name, double price, String category, Set<Ingredient> ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return name.equals(menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
