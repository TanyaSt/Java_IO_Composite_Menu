package com.telran.db;

import com.telran.dao.entity.Ingredient;
import com.telran.dao.entity.MenuItem;
import com.telran.dao.entity.ProductUnit;
import com.telran.db.entity.IngredientDoc;
import com.telran.db.entity.MenuItemDoc;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class Mapper {
    public static MenuItem map(MenuItemDoc doc){
        return new MenuItem(doc.getName(),
                doc.getPrice(),
                doc.getCategory(),
                Arrays.stream(doc.getIngredients())
                        .map(Mapper::map)
                        .collect(Collectors.toSet()));
    }

    private static Ingredient map(IngredientDoc doc){
        return new Ingredient(doc.getName(),doc.getCount(), ProductUnit.valueOf(doc.getUnit().toUpperCase(Locale.ROOT)));
    }

    public static MenuItemDoc map(MenuItem item){
        return new MenuItemDoc(item.getName(),
                item.getPrice(),
                item.getCategory(),
                item.getIngredients().stream().map(Mapper::map).toArray(IngredientDoc[]::new)
                );
    }

    private static IngredientDoc map(Ingredient ingredient){
        return new IngredientDoc(ingredient.getName(), ingredient.getCount(),ingredient.getUnit().toString());
    }
}
