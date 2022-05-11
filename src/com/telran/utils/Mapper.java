package com.telran.utils;

import com.telran.dao.entity.Ingredient;
import com.telran.dao.entity.MenuItem;
import com.telran.dao.entity.ProductEntity;
import com.telran.dao.entity.ProductUnit;
import com.telran.service.pojo.IngredientPojo;
import com.telran.service.pojo.MenuItemPojo;
import com.telran.service.pojo.ProductPojo;
import com.telran.service.pojo.ProductUnitPojo;

import static java.util.stream.Collectors.toSet;

public class Mapper {
    private Mapper(){
        throw new UnsupportedOperationException();
    }
    public static Ingredient map(IngredientPojo pojo){
        return new Ingredient(pojo.getName(), pojo.getCount(), ProductUnit.valueOf(pojo.getUnit().name()));
    }

    public static IngredientPojo map(Ingredient ingredient){
        return IngredientPojo.of(ingredient.getName(),ingredient.getCount(), ProductUnitPojo.valueOf(ingredient.getUnit().name()));
    }

    public static MenuItemPojo map(MenuItem item){
        return MenuItemPojo.of(item.getName(),item.getPrice(), item.getCategory(), item.getIngredients().stream().map(Mapper::map).collect(toSet()));
    }

    public static ProductPojo map(ProductEntity product){
        return ProductPojo.of(product.getName(),product.getCount(),ProductUnitPojo.valueOf(product.getUnit().name()));
    }
}
