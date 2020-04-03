package com.daniel.assignment.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

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
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/items")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldSignUpUsers() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\":\"password\"}");
        mvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/items").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private void signUpUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"admin\", \"password\":\"password\"}");
        mvc.perform(builder).andExpect(status().isOk());
    }
}
