package com.telran.dao.crm;

import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.MenuItem;
import com.telran.dao.entity.UserOrderEntity;
import com.telran.dao.entity.UserOrderStatus;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class CRMImpl implements CRM {
    private final TreeMap<LocalDate,Map<UUID,DeliveryOrderEntity>> deliveries = new TreeMap<>();
    private final TreeMap<LocalDate,Map<UUID,UserOrderEntity>> userOrdersByDate = new TreeMap<>();
    private final Map<UUID,UserOrderEntity> userOrders = new HashMap<>();

    @Override
    public void addDeliveryOrder(DeliveryOrderEntity order) {
        deliveries.computeIfAbsent(
                Objects.requireNonNull(order).getDate().toLocalDate(),
                k -> new HashMap<>()
                ).put(order.getId(),order);
    }

    @Override
    public Stream<DeliveryOrderEntity> getDeliveryOrderPeriod(LocalDate from, LocalDate to) {
        if(Objects.requireNonNull(from).isAfter(Objects.requireNonNull(to))){
            throw new IllegalArgumentException("Wrong date format");
        }

        return deliveries.subMap(from,true,to,true)
                .values().stream()
                .flatMap(m -> m.values().stream());
    }

    @Override
    public Stream<UserOrderEntity> getUserOrdersPeriod(LocalDate from, LocalDate to) {
        if(Objects.requireNonNull(from).isAfter(Objects.requireNonNull(to))){
            throw new IllegalArgumentException("Wrong date format");
        }
        return userOrdersByDate.subMap(from,true,to,true)
                .values().stream()
                .flatMap(m -> m.values().stream());
    }

    @Override
    public Stream<UserOrderEntity> getUserOrdersPeriodByStatus(LocalDate from, LocalDate to, UserOrderStatus status) {
        return getUserOrdersPeriod(from,to)
                .filter(o -> o.getStatus() == status);
    }

    @Override
    public UUID createNewUserOrder(LocalDateTime dateTime) {
        UUID id = UUID.randomUUID();
        UserOrderEntity value = new UserOrderEntity(id, dateTime, List.of(), UserOrderStatus.NEW);
        userOrders.put(id,value);
        userOrdersByDate.computeIfAbsent(dateTime.toLocalDate(),
                k -> new HashMap<>()).put(id,value);
        return id;
    }

    @Override
    public void updateUserOrder(UUID id, List<MenuItem> items) {
        UserOrderEntity old = userOrders.get(id);
        if(old == null) throw new IllegalArgumentException("Order not found");

        List<MenuItem> list = new ArrayList<>(items);
        list.addAll(old.getMenuItems());

        UserOrderEntity newOrder = new UserOrderEntity(
                old.getId(),
                old.getDate(),
                Collections.unmodifiableList(list),
                UserOrderStatus.PROGRESS);
        userOrders.put(old.getId(),newOrder);
        userOrdersByDate.get(old.getDate().toLocalDate()).put(old.getId(),newOrder);
    }

    @Override
    public UserOrderEntity getOrderById(UUID id) {
        return userOrders.get(id);
    }

    @Override
    public void closeOrder(UUID id) {
        UserOrderEntity old = userOrders.get(id);
        if(old == null) throw new IllegalArgumentException("Order not found");
        UserOrderEntity newOrder = new UserOrderEntity(old.getId(),old.getDate(),old.getMenuItems(),UserOrderStatus.DONE);
        userOrders.put(old.getId(),newOrder);
        userOrdersByDate.get(old.getDate().toLocalDate()).put(old.getId(),newOrder);
    }
}
