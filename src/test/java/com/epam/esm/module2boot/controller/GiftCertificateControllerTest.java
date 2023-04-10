package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(controllers = GiftCertificateController.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class GiftCertificateControllerTest {
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
    void createGiftCertificate() {
    }

    @Test
    void getGiftCertificateById() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/GiftCertificate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("name1"))
                        .andExpect(jsonPath("$.description").value("description1"))
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.price").value(10))
                        .andExpect(jsonPath("$.duration").value(100))
                        .andExpect(jsonPath("$.createDate").value("01-05-2022T12:30:00.000"))
                        .andExpect(jsonPath("$.lastUpdateDate").value("01-04-2022T12:30:00.000"))
                        .andReturn();

        GiftCertificateDTO giftCertificateDTO= objectMapper
                .readValue(result.getResponse().getContentAsString(),GiftCertificateDTO.class);

        assertEquals(3, giftCertificateDTO.getTags().size());

        mockMvc.perform(MockMvcRequestBuilders.get("/GiftCertificate/11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteGiftCertificate() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.delete("/GiftCertificate/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete("/GiftCertificate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    void getAllGiftCertificates() {
    }
}