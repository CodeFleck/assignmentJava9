package com.daniel.assignment.controller;

import com.daniel.assignment.exception.ResourceNotFoundException;
import com.daniel.assignment.model.Item;
import com.daniel.assignment.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemService.getItems();
    }

    @GetMapping("/items/{name}")
    public ResponseEntity<Item> getItemByName(@PathVariable(value = "name") String itemName) throws ResourceNotFoundException {
        Item item = itemService.getItem(itemName);
        if (item == null) {
            throw new ResourceNotFoundException("Item not found :: " + itemName);
        } else {
            return ResponseEntity.ok().body(item);
        }
    }

    @PostMapping("/purchase/{name}")
    public Map<String, Boolean> purchaseItem(
            @PathVariable(value = "name") String itemName) throws ResourceNotFoundException {
        Item item = itemService.getItem(itemName);
        if (item == null) {
            throw new ResourceNotFoundException("Item not found :: " + itemName);
        } else {
            boolean isInStock = itemService.updateStockLevel(item);
            Map<String, Boolean> response = new HashMap<>();
            if (isInStock) {
                response.put("purchased", Boolean.TRUE);
                return response;
            } else {
                response.put("Out of stock", Boolean.FALSE);
                return response;
            }
        }
    }
}