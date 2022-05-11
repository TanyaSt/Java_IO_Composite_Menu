package com.telran.menu;

import java.util.function.Consumer;

public interface Item {
    String getName();
    void perform(InputOutput io);

    default boolean isExit(){return false;}

    static Item of(String name, Consumer<InputOutput> action, boolean isExit){
        return new Item() {
            @Override
            public String getName() {return name;}

            @Override
            public void perform(InputOutput io) {action.accept(io);}

            @Override
            public boolean isExit() {return isExit;}
        };
    }

    static Item of(String name, Consumer<InputOutput> action){
        return of(name, action,false);
    }

    static Item exit(){
        return of("Exit",s -> {}, true);
    }
}
