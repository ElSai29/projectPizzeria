package com.accenture.projectPizzeria.controller.customer;

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
import org.springframework.web.util.UriComponentsBuilder;

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
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_CUSTOMER_ENDPOINT, customerRequestDto, Void.class);

        CustomerResponseDto customerResponseDto = customerService.findByNameCustomer(customerRequestDto.name());


        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post customer must return a 201 http response code.");
            Assertions.assertNotNull(customerResponseDto, "Customer dto response returned from service must not be null");
            Assertions.assertNotNull(customerResponseDto.id(), "Customer return must not have a null UUID");
            Assertions.assertEquals(name,customerResponseDto.name(), "Customer name must match the request name");
            Assertions.assertEquals(email,customerResponseDto.email(), "Customer email must match the request email");
        });
    }

    @Test
    @Order(3)
    @DisplayName("Find all the customers through Get endpoint")
    void testGetAllCustomerSuccess(){
        ResponseEntity<List<CustomerResponseDto>> response = restTemplate.exchange("http://localhost:" + port + API_CUSTOMER_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerResponseDto>>() {
        });
        List<CustomerResponseDto> customers = response.getBody();
        System.err.println("response body: " + response.getBody());
        Assertions.assertAll(()-> {
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals("Edouard", customers.getFirst().name(), "Customer name must match the request name");
            Assertions.assertEquals("edouard@gmail.com", customers.getFirst().email());
        });
    }

    @Test
    @Order(2)
    @DisplayName("Find the customer by his name through Get endpoint")
    void testGetCustomerByNameSuccess(){
        String name = "Edouard";

        String url = UriComponentsBuilder
                .fromUriString("http://localhost:" + port + API_CUSTOMER_ENDPOINT + "/{name}")
                .buildAndExpand(name)
                .toUriString();

        ResponseEntity<CustomerResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, null, CustomerResponseDto.class);
        Assertions.assertAll(()-> {
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals("Edouard", response.getBody().name());
        });
    }

}
