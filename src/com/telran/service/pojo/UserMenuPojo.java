package com.telran.service.pojo;

import java.util.Objects;
import java.util.Set;

public class UserMenuPojo {
    private final String orderId;
    private final Set<MenuItemPojo> menu;

    private UserMenuPojo(String orderId, Set<MenuItemPojo> menu) {
        this.orderId = orderId;
        this.menu = menu;
    }

    public static UserMenuPojo of(String id, Set<MenuItemPojo> menu){
        return new UserMenuPojo(Objects.requireNonNull(id),Objects.requireNonNull(menu));
    }

    public String getOrderId() {
        return orderId;
    }

    public Set<MenuItemPojo> getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMenuPojo that = (UserMenuPojo) o;
        return orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "UserMenuPojo{" +
                "id='" + orderId + '\'' +
                ", menu=" + menu +
                '}';
    }
}
