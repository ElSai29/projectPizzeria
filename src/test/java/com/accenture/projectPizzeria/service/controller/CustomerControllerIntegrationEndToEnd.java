package com.accenture.projectPizzeria.service.controller;

import com.accenture.projectPizzeria.service.CustomerServiceImpl;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@AutoConfigureTestRestTemplate

@ActiveProfiles("test")

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerIntegrationEndToEnd {

    private static final String API_CUSTOMER_ENDPOINT = "/customers";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    @Order(1)
    @DisplayName("Creates a Customer through Post endpoint")
    void testPostCustomerSuccess(){

        String name = "Edouard";
        String email = "edouard@gmail.com";

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(name, email);
        CustomerResponseDto customerResponseDto = customerService.addCustomer(customerRequestDto);
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_CUSTOMER_ENDPOINT, customerResponseDto, Void.class);



        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post customer must return a 201 http response code.");
            Assertions.assertNotNull(customerResponseDto, "Customer dto response returned from service must not be null");
            Assertions.assertNotNull(customerResponseDto.id(), "Customer return must not have a null UUID");
            Assertions.assertEquals(name,customerResponseDto.name(), "Customer name must match the request name");
            Assertions.assertEquals(email,customerResponseDto.email(), "Customer email must match the request email");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Find all the customers through Get endpoint")
    void testGetAllCustomerSuccess(){
        ResponseEntity<List<CustomerResponseDto>> response = restTemplate.exchange("http://localhost:" + port + API_CUSTOMER_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerResponseDto>>() {
        });
        List<CustomerResponseDto> customers = response.getBody();
        System.err.println("response body: " + response.getBody());
        Assertions.assertAll(()-> {
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals("Edouard", customers.getFirst().name());
            Assertions.assertEquals("edouard@gmail.com", customers.getFirst().email());
        });
    }
}
