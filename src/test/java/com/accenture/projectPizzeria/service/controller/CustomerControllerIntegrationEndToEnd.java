package com.accenture.projectPizzeria.service.controller;

import com.accenture.projectPizzeria.service.CustomerServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureTestRestTemplate

@ActiveProfiles("test")

@TestMethodOrder(MethodOrderer.class)
public class CustomerControllerIntegrationEndToEnd {

    private static final String API_CUSTOMER_ENDPOINT = "/customers";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    @Order(1)
    @DisplayName("Creates a Customer throught Post endpoint")
    void testPostCustomerSuccess(){

    }
}
