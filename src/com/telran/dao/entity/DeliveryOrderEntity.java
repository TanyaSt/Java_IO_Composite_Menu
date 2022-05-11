package com.telran.dao.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class DeliveryOrderEntity {
    private final UUID id;
    private final LocalDateTime date;
    private final Set<ProductEntity> products;

    public DeliveryOrderEntity(LocalDateTime date, Set<ProductEntity> products) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryOrderEntity that = (DeliveryOrderEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DeliveryOrderEntity{" +
                "id=" + id +
                ", date=" + date +
                ", products=" + products +
                '}';
    }
}
