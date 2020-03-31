package com.daniel.assignment.service;

import com.daniel.assignment.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    public void shouldReturnAllItems() {
        List<Item> itemList = itemService.getItems();

        assertThat(itemList.size()).isEqualTo(3);
    }

    @Test
    public void shouldReturnAnItem() {
        Item item = itemService.getItem("Shirt");

        assertThat(item.getName()).isEqualTo("Shirt");
        assertThat(item.getDescription()).isEqualTo("medium");
        assertThat(item.getPrice()).isEqualTo(20);
    }

    @Test
    public void shouldUpdateStockLevel() {
        Item item1 = new Item("Shirt", "medium", 20);

        boolean response = itemService.updateStockLevel(item1);

        assertThat(response).isEqualTo(true);
    }
}
