package com.telran.dao.entity;

import java.util.Objects;

public class ProductEntity {
    private final String name;
    private final ProductUnit unit;
    private final double pricePerUnit;
    private final double count;

    public ProductEntity(String name, ProductUnit unit, double pricePerUnit, double count) {
        this.name = name;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "name='" + name + '\'' +
                ", unit=" + unit +
                ", price=" + pricePerUnit +
                ", count=" + count +
                '}';
    }
}
