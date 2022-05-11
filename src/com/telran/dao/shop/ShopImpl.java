package com.telran.dao.shop;

import com.telran.dao.entity.ProductEntity;
import com.telran.dao.entity.ProductUnit;

import java.util.HashMap;
import java.util.Map;

public class ShopImpl implements Shop {
    private final Map<String,ProductEntity> map;

    public ShopImpl(){
        map = new HashMap<>();
        map.put("Potato",new ProductEntity("Potato", ProductUnit.KG,10,0));
        map.put("Milk",new ProductEntity("Milk", ProductUnit.LITRE,5,0));
        map.put("Pasta",new ProductEntity("Pasta", ProductUnit.KG,15,0));
        map.put("Oil",new ProductEntity("Oil", ProductUnit.LITRE,12,0));
        map.put("Chicken",new ProductEntity("Chicken", ProductUnit.KG,23,0));
        map.put("Egg",new ProductEntity("Egg", ProductUnit.UNIT,3,0));
    }

    @Override
    public ProductEntity getProductByName(String name) {
        return map.get(name);
    }
}
