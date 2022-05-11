package com.telran.service.user;

import com.telran.dao.crm.CRM;
import com.telran.dao.entity.MenuItem;
import com.telran.dao.menu.Menu;
import com.telran.service.pojo.OrderItemPojo;
import com.telran.service.pojo.UserMenuPojo;
import com.telran.utils.Mapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class UserServiceImpl implements UserService {
    private final Menu menu;
    private final CRM crm;

    public UserServiceImpl(Menu menu, CRM crm) {
        this.menu = menu;
        this.crm = crm;
    }

    @Override
    public UserMenuPojo getMenu() {
        UUID id = crm.createNewUserOrder(LocalDateTime.now());
        return UserMenuPojo.of(id.toString(),menu.getAll().map(Mapper::map).collect(toSet()));
    }

    @Override
    public void makeOrder(String orderId, Map<String, Integer> menuItems) {
        List<MenuItem> list = menu.getAll().filter(mi -> menuItems.containsKey(mi.getName())).collect(toList());
        List<MenuItem> res =new ArrayList<>();
        list.forEach(i -> res.addAll(Collections.nCopies(menuItems.get(i.getName()),i)));
        crm.updateUserOrder(UUID.fromString(orderId), res);
    }

    @Override
    public Stream<OrderItemPojo> getOrderStatus(String orderId) {
        List<MenuItem> list = crm.getOrderById(UUID.fromString(orderId)).getMenuItems();
        Map<MenuItem,Integer> map = new HashMap<>();
        list.forEach(i -> map.merge(i,1,Integer::sum));
        return map.entrySet()
                .stream()
                .map(entry -> OrderItemPojo.of(
                        entry.getKey().getName(),
                        entry.getKey().getPrice(),
                        entry.getValue()
                ));
    }

    @Override
    public void closeOrder(String orderId) {
        crm.closeOrder(UUID.fromString(orderId));
    }
}
