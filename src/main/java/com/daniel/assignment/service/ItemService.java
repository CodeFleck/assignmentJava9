package com.daniel.assignment.service;

import com.daniel.assignment.dao.ItemDao;
import com.daniel.assignment.model.Item;
import com.daniel.assignment.util.ItemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemDao itemDao;

    public List<Item> getItems() {
        List<Item> listOfItems = itemDao.getAllItems();
        return listOfItems;
    }

    public Item getItem(String itemName) {
        Item item = itemDao.getItem(itemName);
        HashMap<Item, Integer> itemMap = ItemUtil.increaseVisualizationCounter(item);
        if (itemMap.get(item) > 10 && itemMap.get(item) < 12){
            item.setPrice(Math.round(item.getPrice()*1.1));
        }
        return item;
    }

    public boolean updateStockLevel(Item item) {
        HashMap<Item, Integer> itemStockLevelMaps = ItemUtil.decreaseInventory(item);
        System.out.println(item.getName() + " - stock level: " + itemStockLevelMaps.get(item));
        if (itemStockLevelMaps.get(item) <= 0) {
            return false;
        }
        return true;
    }
}
