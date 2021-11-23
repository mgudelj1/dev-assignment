package com.example.developerassignment;

import com.example.developerassignment.endpoints.dtos.SearchOrderParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DeveloperAssignmentApplicationTests {

    public static final int INITIAL_PRODUCT_COUNT = 10;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    void contextLoads() {
        //Could possibly initialize orders and products and other objects
    }

    //Product controller tests
    @Test
    public void shouldReturnNikeProduct() throws Exception {
        this.mockMvc.perform(get("/api/products/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Nike running shoes")));
    }

    @Test
    public void shouldReturnAllProduct() throws Exception {
        this.mockMvc.perform(get("/api/products")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(INITIAL_PRODUCT_COUNT)));
    }

    @Test
    @Transactional
    public void shouldStoreNewProduct() throws Exception {
        String testProduct = "{\"name\": \"New fancy shoe\",\"priceEur\": 224.33}";
        this.mockMvc.perform(post("/api/products")
                .content(testProduct)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    //Order controller tests
    @Test
    public void shouldReturnNoOrders() throws Exception {
        this.mockMvc.perform(get("/api/orders")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateNewOrder() throws Exception {
        String testOrder = "{\"email\": \"mgudelj1@vip.hr\",\"orderItemList\": [{\"product\":{\"id\": \"1\"},\"quantity\": 5},{\"product\":{\"id\": \"7\"},\"quantity\": 3}]}";
        this.mockMvc.perform(post("/api/orders")
                .content(testOrder)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void shouldCreateTwoOrdersAndFetchOne() throws Exception {
        String firstOrder = "{\"email\": \"mgudelj1@vip.hr\",\"orderItemList\": [{\"product\":{\"id\": \"1\"},\"quantity\": 5},{\"product\":{\"id\": \"7\"},\"quantity\": 3}]}";
        String secondOrder = "{\"email\": \"mgudelj1@vip.hr\",\"orderItemList\": [{\"product\":{\"id\": \"1\"},\"quantity\": 5},{\"product\":{\"id\": \"7\"},\"quantity\": 3}]}";
        final LocalDateTime start = LocalDateTime.now();
        //Not a good practice in tests
        Thread.sleep(100);
        this.mockMvc.perform(post("/api/orders")
                .content(firstOrder)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
        Thread.sleep(250);
        final LocalDateTime end = LocalDateTime.now();
        Thread.sleep(250);
        this.mockMvc.perform(post("/api/orders")
                .content(secondOrder)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

        this.mockMvc.perform(post("/api/orders/dateRange")
                .content(asJsonString(new SearchOrderParams(start, end)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}