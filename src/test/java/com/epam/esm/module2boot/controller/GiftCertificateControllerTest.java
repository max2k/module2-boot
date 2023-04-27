package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.stream.Stream;

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

    @Autowired
    private GiftCertificateService giftCertificateService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static Stream<Arguments> queryParams() {
        return Stream.of(
                Arguments.of(
                        Map.of("description", "description2"), 4
                ),
                Arguments.of(
                        Map.of("tags", "tag1"), 2
                ),
                Arguments.of(
                        null, 25
                )

        );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createGiftCertificate() throws Exception {
        String query = """
                {
                   "name" : "test name",
                   "description" : "test description",
                   "price" : 11.11,
                   "duration": 100,
                   "createDate": "2022-02-02T10:11:12.000",
                   "lastUpdateDate": "2022-02-02T10:11:12.000",
                   "tags": [
                        { "name": "tag2"  },
                        { "name": "tag33" },
                        { "name": "tag1"  }
                    ]
                }
                """;
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/GiftCertificate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(query))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test name"))
                .andExpect(jsonPath("$.description").value("test description"))
                .andExpect(jsonPath("$.price").value(11.11))
                .andExpect(jsonPath("$.duration").value(100))
                .andExpect(jsonPath("$.createDate").value("2022-02-02T10:11:12.000"))
                .andExpect(jsonPath("$.lastUpdateDate").value("2022-02-02T10:11:12.000"))
                .andReturn();

        GiftCertificateDTO giftCertificateDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), GiftCertificateDTO.class);

        assertEquals(3, giftCertificateDTO.getTags().size());

    }

    @Test
    void getGiftCertificateById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/GiftCertificate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.duration").value(100))
                .andExpect(jsonPath("$.createDate").value("2022-05-01T12:30:00.000"))
                .andExpect(jsonPath("$.lastUpdateDate").value("2022-04-01T12:30:00.000"))
                .andReturn();

        GiftCertificateDTO giftCertificateDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(), GiftCertificateDTO.class);

        assertEquals(4, giftCertificateDTO.getTags().size());

        mockMvc.perform(MockMvcRequestBuilders.get("/GiftCertificate/1000011")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void deleteGiftCertificate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/GiftCertificate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete("/GiftCertificate/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @ParameterizedTest
    @MethodSource("queryParams")
    void getAllGiftCertificates(Map<String, String> params, int resultCount) throws Exception {

        MultiValueMap<String, String> httpParam = new LinkedMultiValueMap<>();

        if (params != null) httpParam.setAll(params);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/GiftCertificate")
                                .params(httpParam)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.content.length()").value(resultCount))
                .andExpect(status().isOk())
                .andReturn();


    }

    @Test
    void updateGiftCertificate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/GiftCertificate/1ff11111111"))
                .andExpect(status().isBadRequest())
                .andReturn();


        mockMvc.perform(MockMvcRequestBuilders.put("/GiftCertificate/1")
                        .param("name", "new name")
                        .param("description", "new description")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        GiftCertificate giftCertificate=giftCertificateService.getGiftCertificateById(1);

        assertEquals("new name",giftCertificate.getName());
        assertEquals("new description",giftCertificate.getDescription());
    }
}