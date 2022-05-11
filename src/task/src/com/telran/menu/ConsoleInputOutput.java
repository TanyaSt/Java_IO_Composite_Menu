package com.telran.menu;

import java.util.Scanner;

public class ConsoleInputOutput implements InputOutput{
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String readString(String prompt) {
        write(prompt + " > ");
        return scanner.nextLine();
    }

    @Override
    public void write(Object obj) {
        System.out.print(obj);
    }
}
