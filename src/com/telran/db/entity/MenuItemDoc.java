package com.telran.db.entity;

import java.util.Arrays;

public class MenuItemDoc {
    String name;
    double price;
    String category;
    IngredientDoc[] ingredients;

    public MenuItemDoc() {
    }

    public MenuItemDoc(String name, double price, String category, IngredientDoc[] ingredients) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public IngredientDoc[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientDoc[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "MenuItemDoc{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                '}';
    }
}
