package com.telran.db;

import com.telran.dao.entity.MenuItem;

import java.io.Closeable;
import java.util.List;

public interface StoreProvider extends Closeable {
    void saveMenu(MenuItem item);
    void saveAll(List<MenuItem> items);
    List<MenuItem> getAllMenuItems();
}
