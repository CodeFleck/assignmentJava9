package com.daniel.assignment.util;

import com.daniel.assignment.model.Item;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class ItemUtil {

    static HashMap<Item, Integer> itemVisualizationCounterMap = new HashMap<>();
    static HashMap<Item, Integer> itemStockMap = new HashMap<>();
    static HashMap<Item, LocalDateTime> itemVisualizationTimeStampMap = new HashMap<>();

    public static HashMap<Item, Integer> increaseVisualizationCounter(Item item) {
        if (!itemVisualizationCounterMap.containsKey(item)){
            itemVisualizationCounterMap.put(item, 1);
            itemVisualizationTimeStampMap.put(item, LocalDateTime.now());
        } else {
            LocalDateTime timeOfFirstVisualization = itemVisualizationTimeStampMap.get(item);
            if (LocalDateTime.now().isAfter(timeOfFirstVisualization.plus(1, ChronoUnit.HOURS))){
                itemVisualizationCounterMap.put(item, 1);
            } else {
                itemVisualizationCounterMap.put(item, itemVisualizationCounterMap.get(item).intValue() + 1);
            }
        }
        return itemVisualizationCounterMap;
    }

    public static HashMap<Item, Integer> decreaseInventory(Item item) {
        if (!itemStockMap.containsKey(item)){
            itemStockMap.put(item, 4);
        } else {
            itemStockMap.put(item, itemStockMap.get(item).intValue()-1);
        }
        return itemStockMap;
    }
}
