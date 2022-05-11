package com.telran.service.admin;

import com.telran.service.pojo.IngredientPojo;
import com.telran.service.pojo.MenuItemPojo;
import com.telran.service.pojo.ProductPojo;
import com.telran.service.pojo.ReportPojo;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface AdminService {
    void addMenuItem(String name, double price, String category, Set<IngredientPojo> ingredients);
    void updateMenuItem(String name, double price,String category, Set<IngredientPojo> ingredients);
    void deleteMenuItem(String name);
    Stream<MenuItemPojo> getMenu();
    void makeProductDelivery(Map<String,Double> products);
    Stream<ProductPojo> getStoreStatus();
    Stream<ReportPojo> getReport(LocalDate from, LocalDate to);
    Stream<ReportPojo> getReport(LocalDate from, LocalDate to, ReportPojo.Type type);
}
