package com.telran.service.user;

import com.telran.service.pojo.OrderItemPojo;
import com.telran.service.pojo.UserMenuPojo;

import java.util.Map;
import java.util.stream.Stream;

public interface UserService {
    UserMenuPojo getMenu();
    void makeOrder(String orderId, Map<String,Integer> menuItems);
    Stream<OrderItemPojo> getOrderStatus(String orderId);
    void closeOrder(String orderId);
}
