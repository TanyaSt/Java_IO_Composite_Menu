package com.telran.menu;

import java.util.Arrays;
import java.util.List;

public class Menu implements Item{
    String name;
    List<Item> items;

    public Menu(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public Menu(String name, Item... items) {
        this.name = name;
        this.items = Arrays.asList(items);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform(InputOutput io) {
        while (true){
            displayMenu(io);
            int optionId = io.readInt(" Select ID",1,items.size());
            Item item = items.get(optionId - 1);
            try {
                item.perform(io);
            }catch(Exception e){
                io.writeln(e.getMessage());
            }
            if(item.isExit()){
                break;
            }
        }
    }

    private void displayMenu(InputOutput io){
        io.writeln("=================");
        io.writeln(" " + name);
        io.writeln("=================");
        int i = 1;
        for (Item item : items) {
            io.writeln(String.format(" %2d.%s",i++,item.getName()));
        }
        io.writeln("=================");
    }
}
