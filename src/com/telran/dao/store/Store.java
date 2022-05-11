package com.telran.dao.store;

import com.telran.dao.entity.ProductEntity;

import java.util.Set;
import java.util.stream.Stream;

public interface Store {
    void addToStore(ProductEntity product);
    void addToStore(Set<ProductEntity> products);
    ProductEntity getProductByName(String name);
    Stream<ProductEntity> getStoreStatus();
}
