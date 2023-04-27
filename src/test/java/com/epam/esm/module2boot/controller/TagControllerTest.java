package com.epam.esm.module2boot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TagControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getTagById() throws Exception {
        mockMvc.perform(get("/tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("tag1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/tags/1"))
                .andReturn();

        mockMvc.perform(get("/tags/1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTag() throws Exception {
        String newTagJson = """
                    {
                        "name": "test tag name"
                    }
                """;
        mockMvc.perform(post("/tags/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTagJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1005))
                .andExpect(jsonPath("$.name").value("test tag name"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/tags/1005"))
                .andReturn();

        mockMvc.perform(post("/tags/new")   // tag with this name already exists
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTagJson))
                .andExpect(status().isBadRequest())
                .andReturn();


        mockMvc.perform(post("/tags/new") // prevent update of existing tag
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\" : 1,  \"name\": \"changedName\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/tags/new") // prevent update of existing tag
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\" : 1  }"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deleteTag() throws Exception {
        mockMvc.perform(delete("/tags/1") // delete existing tag
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(delete("/tags/1") // delete existing tag
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getAllTags() throws Exception {
        mockMvc.perform(get("/tags/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tagList").isArray())
                .andExpect(jsonPath("$._embedded.tagList", hasSize(10)))
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/tags/all?page=0&size=10"))
                .andReturn();

        mockMvc.perform(get("/tags/all")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tagList").isArray())
                .andExpect(jsonPath("$._embedded.tagList", hasSize(5)))
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost/tags/all?page=1&size=5"))
                .andReturn();
    }
}