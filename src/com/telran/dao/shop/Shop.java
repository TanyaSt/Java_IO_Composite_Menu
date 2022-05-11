package com.telran.dao.shop;

import com.telran.dao.entity.ProductEntity;

public interface Shop {
    ProductEntity getProductByName(String name);
}
