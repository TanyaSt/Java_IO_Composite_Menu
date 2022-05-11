package com.telran.service.pojo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReportPojo {
    private final String id;
    private final Type type;
    private final LocalDateTime date;
    private final List<String> items;
    private final double totalAmount;

    private ReportPojo(String id, Type type, LocalDateTime date, List<String> items, double totalAmount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public static ReportPojo of(String id, Type type, LocalDateTime date, List<String> items, double totalAmount){
        return new ReportPojo(
                Objects.requireNonNull(id),
                Objects.requireNonNull(type),
                Objects.requireNonNull(date),
                Objects.requireNonNull(items),
                totalAmount
        );
    }

    public enum Type{
        Delivery,Order;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<String> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "ReportPojo{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
