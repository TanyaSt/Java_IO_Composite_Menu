package com.telran.dao.crm;

import com.telran.dao.entity.DeliveryOrderEntity;
import com.telran.dao.entity.MenuItem;
import com.telran.dao.entity.UserOrderEntity;
import com.telran.dao.entity.UserOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CRM {
    //For Admin
    void addDeliveryOrder(DeliveryOrderEntity order);
    Stream<DeliveryOrderEntity> getDeliveryOrderPeriod(LocalDate from, LocalDate to);
    Stream<UserOrderEntity> getUserOrdersPeriod(LocalDate from, LocalDate to);
    Stream<UserOrderEntity> getUserOrdersPeriodByStatus(LocalDate from, LocalDate to, UserOrderStatus status);

    //For user
    UUID createNewUserOrder(LocalDateTime dateTime);
    void updateUserOrder(UUID id, List<MenuItem> items);
    UserOrderEntity getOrderById(UUID id);
    void closeOrder(UUID id);
}
