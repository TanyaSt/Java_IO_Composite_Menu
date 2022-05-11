package com.telran.service.pojo;

import java.util.Objects;

public class ProductPojo {
    private final String name;
    private final double count;
    private final ProductUnitPojo unit;

    private ProductPojo(String name, double count, ProductUnitPojo unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    public static ProductPojo of(String name, double count, ProductUnitPojo unit){
        return new ProductPojo(Objects.requireNonNull(name),count,Objects.requireNonNull(unit));
    }

    public String getName() {
        return name;
    }

    public double getCount() {
        return count;
    }

    public ProductUnitPojo getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPojo that = (ProductPojo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ProductPojo{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit=" + unit +
                '}';
    }
}
