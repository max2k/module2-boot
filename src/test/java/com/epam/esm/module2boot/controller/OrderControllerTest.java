package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.model.Tag;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class OrderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static Stream<Arguments> createOrderParams() {
        return Stream.of(
                Arguments.of(  // normal
                        """
                                {
                                   "id" : -1,
                                   "userId" : 2,
                                   "giftCertificateId" : 1,
                                   "cost" : "11.11" }
                                """, 0
                ),
                Arguments.of( // uniques user_id and giftCertificate id constraint
                        """
                                {
                                   "id" : -1,
                                   "userId" : 807,
                                   "giftCertificateId" : 9230,
                                   "cost" : "11.11" }
                                """, 1

                ),

                Arguments.of( // primary key violation
                        """
                                {
                                   "id" : 22,
                                   "userId" : 1,
                                   "giftCertificateId" : 1,
                                   "cost" : "11.11" }
                                """, 1
                ),
                Arguments.of( // not null constraint
                        """
                                {
                                   "id" : -1,
                                   "userId" : 1,
                                   "giftCertificateId" : 1,
                                   "cost" : null }
                                """, 1

                )
        );
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @ParameterizedTest
    @MethodSource("createOrderParams")
    void createOrder(String query, int expected) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(query))
                .andExpect(expected == 0 ? status().isCreated() : status().isBadRequest())
                .andReturn();
    }

    @Test
    void getMostWidelyUsedTag() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders/mostWidelyUsedTag")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Tag tag = objectMapper.readValue(result.getResponse().getContentAsString(), Tag.class);

        assertEquals("most valuable tag 1", tag.getName());
    }

    @Test
    void getOrdersByUserId() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders.get("/orders/listByUserID/1111323")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform( MockMvcRequestBuilders.get("/orders/listByUserID/323")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserOrderList").isArray())
                .andExpect(jsonPath("$._embedded.UserOrderList",hasSize(3)))
                .andExpect(jsonPath("$._embedded.UserOrderList[0].id").value(311))
                .andExpect(jsonPath("$._embedded.UserOrderList[0].cost").value(111.96))
                .andExpect(jsonPath(
                        "$._embedded.UserOrderList[0]._links.GetOrderDetails.href")
                        .value("http://localhost/orders/311"));

        mockMvc.perform( MockMvcRequestBuilders.get("/orders/listByUserID/323")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserOrderList").isArray())
                .andExpect(jsonPath("$._embedded.UserOrderList",hasSize(2)))
                .andExpect(jsonPath("$._embedded.UserOrderList[0].id").value(311))
                .andExpect(jsonPath("$._embedded.UserOrderList[0].cost").value(111.96))
                .andExpect(jsonPath(
                        "$._embedded.UserOrderList[0]._links.GetOrderDetails.href")
                        .value("http://localhost/orders/311"));

    }

    @Test
    void getOrder() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.get("/orders/311")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(311))
                .andExpect(jsonPath("$.user.id").value(323))
                .andExpect(jsonPath("$.giftCertificate.id").value(2301))
                .andExpect(jsonPath("$.giftCertificate.tags[0].id").value(192))
                .andExpect(jsonPath(
                        "$._links.self.href")
                        .value("http://localhost/orders/311"));

        mockMvc.perform( MockMvcRequestBuilders.get("/orders/31333331")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}