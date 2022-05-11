package com.telran.db.entity;

public class IngredientDoc {
    String name;
    double count;
    String unit;

    public IngredientDoc() {
    }

    public IngredientDoc(String name, double count, String unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "IngredientDoc{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unit='" + unit + '\'' +
                '}';
    }
}
