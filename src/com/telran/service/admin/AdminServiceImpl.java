package com.telran.service.admin;

import com.telran.dao.crm.CRM;
import com.telran.dao.entity.*;
import com.telran.dao.menu.Menu;
import com.telran.dao.shop.Shop;
import com.telran.dao.shop.ShopImpl;
import com.telran.dao.store.Store;
import com.telran.service.pojo.*;
import com.telran.utils.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import com.telran.utils.Mapper;

public class AdminServiceImpl implements AdminService {
    private final Shop shop;
    private final Store store;
    private final CRM crm;
    private final Menu menu;

    public AdminServiceImpl(Shop shop, Store store, CRM crm, Menu menu) {
        this.shop = shop;
        this.store = store;
        this.crm = crm;
        this.menu = menu;
    }

    @Override
    public void addMenuItem(String name, double price, String category, Set<IngredientPojo> ingredients) {
        menu.addMenuItem(new MenuItem(
                requireNonNull(name),
                price,
                requireNonNull(category),
                requireNonNull(ingredients).stream()
                .map(Mapper::map)
                .collect(toSet())
                ));
    }

    @Override
    public void updateMenuItem(String name, double price, String category, Set<IngredientPojo> ingredients) {
        menu.updateMenuItem(new MenuItem(
               requireNonNull(name),
               price,
               requireNonNull(category),
               requireNonNull(ingredients).stream()
               .map(Mapper::map)
               .collect(toSet())
        ));
    }

    @Override
    public void deleteMenuItem(String name) {
        menu.deleteMenuItem(name);
    }

    @Override
    public Stream<MenuItemPojo> getMenu() {
        return menu.getAll()
                .map(Mapper::map);
    }

    @Override
    public void makeProductDelivery(Map<String, Double> products) {
        Set<ProductEntity> productEntities = new HashSet<>();
        for (var entity : products.entrySet()) {
            ProductEntity tmp = shop.getProductByName(entity.getKey());
            productEntities.add(
                    new ProductEntity(tmp.getName(),tmp.getUnit(),tmp.getPricePerUnit(),entity.getValue())
            );
        }
        crm.addDeliveryOrder(new DeliveryOrderEntity(LocalDateTime.now(),productEntities));
        store.addToStore(productEntities);
    }

    @Override
    public Stream<ProductPojo> getStoreStatus() {
        return store.getStoreStatus().map(Mapper::map);
    }

    @Override
    public Stream<ReportPojo> getReport(LocalDate from, LocalDate to) {
        Stream<ReportPojo> stream1 = crm.getDeliveryOrderPeriod(from,to)
                .map(doe -> ReportPojo.of(
                        doe.getId().toString(),
                        ReportPojo.Type.Delivery,
                        doe.getDate(),
                        doe.getProducts().stream().map(ProductEntity::getName).collect(toList()),
                        doe.getProducts().stream().mapToDouble(p -> p.getCount() * p.getPricePerUnit()).sum()
                        )
                );

        Stream<ReportPojo> stream2 = crm.getUserOrdersPeriod(from,to)
                .map(uop -> ReportPojo.of(
                        uop.getId().toString(),
                        ReportPojo.Type.Order,
                        uop.getDate(),
                        uop.getMenuItems().stream().map(MenuItem::getName).collect(toList()),
                        uop.getMenuItems().stream().mapToDouble(MenuItem::getPrice).sum()
                        )
                );
        return Stream.concat(stream1,stream2);
    }

    @Override
    public Stream<ReportPojo> getReport(LocalDate from, LocalDate to, ReportPojo.Type type) {
        return getReport(from, to).filter(report -> report.getType() == type);
    }


}
