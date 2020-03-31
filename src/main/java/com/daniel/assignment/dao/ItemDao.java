package com.daniel.assignment.dao;

import com.daniel.assignment.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemDao {

    private static final Map<String, Item> itemMap = new HashMap<>();

    static {
        initItems();
    }

    private static void initItems() {
        Item item1 = new Item("Shirt", "medium", 20);
        Item item2 = new Item("Pants", "large", 90);
        Item item3 = new Item("Shoes", "leather", 120);

        itemMap.put(item1.getName(), item1);
        itemMap.put(item2.getName(), item2);
        itemMap.put(item3.getName(), item3);
    }

    public static Item getItem(String name) {
        return itemMap.get(name);
    }

    public static List<Item> getAllItems() {
        Collection<Item> c = itemMap.values();
        List<Item> list = new ArrayList<Item>();
        list.addAll(c);
        return list;
    }
}
