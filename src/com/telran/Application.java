package com.telran;

import com.telran.dao.crm.CRM;
import com.telran.dao.crm.CRMImpl;
import com.telran.dao.entity.Ingredient;
import com.telran.dao.entity.ProductUnit;
import com.telran.dao.menu.Menu;
import com.telran.dao.menu.MenuImpl;
import com.telran.dao.shop.Shop;
import com.telran.dao.shop.ShopImpl;
import com.telran.dao.store.Store;
import com.telran.dao.store.StoreImpl;
import com.telran.db.StoreProvider;
import com.telran.db.StoreProviderImpl;
import com.telran.service.admin.AdminService;
import com.telran.service.admin.AdminServiceImpl;
import com.telran.service.pojo.IngredientPojo;
import com.telran.service.pojo.ProductUnitPojo;

import java.util.Set;

public class Application {
    public static void main(String[] args) {
        StoreProvider provider = new StoreProviderImpl();
        Store store = new StoreImpl();
        Shop shop = new ShopImpl();
        CRM crm = new CRMImpl();
        Menu menu = new MenuImpl(provider);
        AdminService adminService = new AdminServiceImpl(shop,store,crm,menu);

//        adminService.addMenuItem("My menu",45.0,"My Cat", Set.of(
//                IngredientPojo.of("My ing1",10.0, ProductUnitPojo.KG),
//                IngredientPojo.of("My ing2",5.5, ProductUnitPojo.KG)
//        ));
//        adminService.addMenuItem("My menu1",35.0,"My Cat", Set.of(
//                IngredientPojo.of("My ing1",10.0, ProductUnitPojo.KG),
//                IngredientPojo.of("My ing2",5.5, ProductUnitPojo.KG)
//        ));

        adminService.getMenu().forEach(System.out::println);
    }
}
