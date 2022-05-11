package com.telran.dao.menu;

import com.telran.dao.entity.MenuItem;
import com.telran.db.StoreProvider;

import java.util.*;
import java.util.stream.Stream;

public class MenuImpl implements Menu {
    private final Map<String,MenuItem> menu = new HashMap<>();
    private final Map<String, Map<String,MenuItem>> menuByCat = new HashMap<>();
    private StoreProvider store;

    public MenuImpl(StoreProvider store) {
        this.store = store;
        List<MenuItem> item = store.getAllMenuItems();
        for (MenuItem menuItem : item) {
            addMenuItem(menuItem);
        }
    }

    @Override
    public void addMenuItem(MenuItem item) {
        if(menu.putIfAbsent(item.getName(),Objects.requireNonNull(item)) != null){
            throw new IllegalArgumentException("Duplicate item");
        }
        menuByCat.computeIfAbsent(item.getCategory(), k -> new HashMap<>()).put(item.getName(),item);
        store.saveMenu(item);
    }

    @Override
    public void updateMenuItem(MenuItem item) {
        deleteMenuItem(item.getName());
        addMenuItem(item);
        store.saveAll(new ArrayList<>(menu.values()));
    }

    @Override
    public void deleteMenuItem(String name) {
        MenuItem item = menu.remove(name);
        if(item == null) throw new IllegalArgumentException(String.format("Item with name: %s does not exists",name));
        menuByCat.get(item.getCategory()).remove(item.getName());
        store.saveAll(new ArrayList<>(menu.values()));
    }

    @Override
    public Stream<MenuItem> getAll() {
        return menu.values().stream();
    }

    @Override
    public Stream<MenuItem> getByCategory(String category) {
        if(!menuByCat.containsKey(category)) throw new IllegalArgumentException("Category does not exists!");
        return menuByCat.get(category).values().stream();
    }
}
