package com.daniel.assignment.controller;

import com.daniel.assignment.model.Item;
import com.daniel.assignment.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ItemService mockItemService;

    final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NjczMzkyNn0." +
            "MDdB1fC8c53IAXg1TkM5Czc4o_nRpqSBhSetucEddpuM4CFmakGAMgP48l5UIVs8IokyRZgxDY8XgYmnvmo9ig";

    @BeforeAll
    public void setup () {
        try {
            signUpUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAllItems() throws Exception {
        Mockito.when(mockItemService.getItems()).thenCallRealMethod();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/items").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        String responseAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> itemList = objectMapper.readValue(responseAsString, List.class);

        assertThat(itemList.size()).isEqualTo(3);
    }

    @Test
    public void shouldGetItemByName() throws Exception {
        Mockito.when(mockItemService.getItem("Shirt")).thenCallRealMethod();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/items/Shirt").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        String responseAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Item item1 = objectMapper.readValue(responseAsString, Item.class);

        assertThat(item1.getName()).isEqualTo("Shirt");
        assertThat(item1.getDescription()).isEqualTo("medium");
        assertThat(item1.getPrice()).isEqualTo(20);
    }

    @Test
    public void shouldPurchaseItem() throws Exception {
        Mockito.when(mockItemService.getItem("Shirt")).thenCallRealMethod();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/purchase/Shirt").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();

        String responseAsString = result.getResponse().getContentAsString();

        assertThat(responseAsString.contains("purchased"));
    }

    @Test
    public void shouldIncreasePriceAfterTenViewsInsideSameHour() throws Exception {
        Mockito.when(mockItemService.getItem("Shirt")).thenCallRealMethod();
        MvcResult result = null;
        for(int i=0; i<11; i++) {
            ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/items/Shirt").header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk());
            result = resultActions.andReturn();
        }
        String responseAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Item item1 = objectMapper.readValue(responseAsString, Item.class);

        assertThat(item1.getPrice()).isEqualTo(22);
    }

    private void signUpUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\":\"password\"}");
        mvc.perform(builder).andExpect(status().isOk());
    }
}
