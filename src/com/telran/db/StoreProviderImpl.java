package com.telran.db;

import com.telran.dao.entity.MenuItem;
import com.telran.db.entity.MenuItemDoc;
import com.telran.json.Json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.telran.db.Mapper.*;

public class StoreProviderImpl implements StoreProvider {
    private BufferedReader reader;
    private BufferedWriter writer;
    private Json json = new Json();
    private static final Path workdir = Path.of("db");
    private static final String MENU_FILE = "menu.json";

    @Override
    public void saveMenu(MenuItem item) {
        Set<MenuItem> items = new HashSet<>(getAllMenuItems());
        items.add(item);
        saveAll(new ArrayList<>(items));
    }

    @Override
    public void saveAll(List<MenuItem> items) {
        try{
            if(!Files.exists(workdir)){
                Files.createDirectory(workdir);
            }

            if(!Files.exists(Path.of(workdir.toString(),MENU_FILE))){
                Files.createFile(Path.of(workdir.toString(),MENU_FILE));
            }
            writer = Files.newBufferedWriter(Path.of(workdir.toString(),MENU_FILE));
            MenuItemDoc[] arr = items.stream().map(Mapper::map).toArray(MenuItemDoc[]::new);

            json.writeToStream(writer, arr);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        try {
            if(!Files.exists(Path.of(workdir.toString(),MENU_FILE))){
                return new ArrayList<>();
            }
            reader = Files.newBufferedReader(Path.of(workdir.toString(),MENU_FILE));
            MenuItemDoc[] items = json.parse(reader, MenuItemDoc[].class);
            return Arrays.stream(items).map(Mapper::map).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if(writer!=null){
            writer.close();
        }
        if(reader != null){
            reader.close();
        }
    }
}
