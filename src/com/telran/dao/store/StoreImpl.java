package com.telran.dao.store;

import com.telran.dao.entity.ProductEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class StoreImpl implements Store {
    private final Map<String, ProductEntity> map = new HashMap<>();

    @Override
    public void addToStore(ProductEntity product) {
        map.merge(
                product.getName(),
                Objects.requireNonNull(product),
                (a,b) -> new ProductEntity(
                        a.getName(),
                        a.getUnit(),
                        a.getPricePerUnit(),
                        a.getCount() + b.getCount()
                )
        );

    }

    @Override
    public void addToStore(Set<ProductEntity> products) {
        Objects.requireNonNull(products)
                .forEach(this::addToStore);
    }

    @Override
    public ProductEntity getProductByName(String name) {
        return map.get(name);
    }

    @Override
    public Stream<ProductEntity> getStoreStatus() {
        return map.values().stream();
    }
}
