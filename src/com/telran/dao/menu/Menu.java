package com.telran.dao.menu;

import com.telran.dao.entity.MenuItem;

import java.util.stream.Stream;

public interface Menu {
    void addMenuItem(MenuItem item);
    void updateMenuItem(MenuItem item);
    void deleteMenuItem(String name);
    Stream<MenuItem> getAll();
    Stream<MenuItem> getByCategory(String category);
}
