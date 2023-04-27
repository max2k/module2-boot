package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getCreateNewUser() throws Exception {
        String newUserJson = """
                    {
                        "firstName": "test user1 name",
                        "lastName": "test user1 surname",
                        "email": "email@email.com"
                    }
                """;
        MvcResult queryResult = mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2001))
                .andExpect(jsonPath("$.firstName").value("test user1 name"))
                .andExpect(jsonPath("$.lastName").value("test user1 surname"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/users/2001"))
                .andReturn();

        String responseJson = queryResult.getResponse().getContentAsString();

        UserDTO userDTO = objectMapper.readValue(responseJson, UserDTO.class);

        userDTO.setEmail("test@test.com");

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2002))
                .andReturn();

        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isBadRequest())
                .andReturn();


    }

    @Test
    void getUser() throws Exception {
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Harriette"))
                .andExpect(jsonPath("$.lastName").value("Tillot"))
                .andExpect(jsonPath("$.email").value("htillot0@mtv.com"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/users/1"))
                .andExpect(jsonPath("$._links.orders.href")
                        .value("http://localhost/orders/listByUserID/1?page=0&size=10"))
                .andReturn();

        mockMvc.perform(get("/users/100000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}