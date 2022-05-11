package com.telran;

import com.telran.menu.ConsoleInputOutput;
import com.telran.menu.InputOutput;
import com.telran.menu.Item;
import com.telran.menu.Menu;

import java.time.LocalDate;

public class App {
    public static void demonstration() {
        InputOutput cio = new ConsoleInputOutput();
        Menu testMenu = new Menu(
                "Main College Menu",
                new Menu(
                        "Lector Menu",
                        Item.of("Timetable", io -> {
                            LocalDate date = io.readDate("For date","yyyy-MM-dd");
                            //Business
                            io.writeln("Display TimeTable for " + date);
                        }),
                        Item.of("Learning Materials",io -> io.writeln("Display Materials")),
                        Item.exit()
                ),
                new Menu(
                        "Student Menu",
                        Item.of("Exam results",io -> io.writeln("Display exam results")),
                        Item.of("Tuition payment",io -> io.writeln("Accept Tuition payment")),
                        Item.exit()
                ),
                Item.of("About",io -> io.writeln("Menu: Composite pattern demonstration")),
                Item.exit()
        );
        testMenu.perform(cio);
    }
}
