package com.telran.service.pojo;

import java.util.Objects;

public class OrderItemPojo {
    private final String name;
    private final double price;
    private final int count;

    private OrderItemPojo(String name, double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public static OrderItemPojo of(String name, double price, int count){
        return new OrderItemPojo(Objects.requireNonNull(name),price,count);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPojo that = (OrderItemPojo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "OrderItemPojo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
