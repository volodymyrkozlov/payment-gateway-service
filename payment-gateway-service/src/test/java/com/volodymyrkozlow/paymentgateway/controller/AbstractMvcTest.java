package com.volodymyrkozlow.paymentgateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AbstractMvcTest {
    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void init() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    protected <T> String writeJson(final T value) {
        return this.mapper.writeValueAsString(value);
    }

    @SneakyThrows
    protected <T> T readJson(final MvcResult mvcResult, final Class<T> clazz) {
        return readJson(mvcResult.getResponse().getContentAsString(), clazz);
    }

    @SneakyThrows
    protected <T> T readJson(final String json, final Class<T> clazz) {
        return this.mapper.readValue(json, clazz);
    }
}
