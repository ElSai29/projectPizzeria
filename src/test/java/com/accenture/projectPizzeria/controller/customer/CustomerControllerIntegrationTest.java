package com.accenture.projectPizzeria.controller.customer;

import com.accenture.projectPizzeria.controller.impl.CustomerController;
import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.service.CustomerServiceImpl;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerIntegrationTest {

    private static final String API_CUSTOMERS_ENDPOINT = "/customers";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerServiceImpl customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerMapper customerMapper;

    @Test
    @DisplayName("Test to persist customer into the postgres")
    void testPersistCustomerSuccess() throws Exception {

        String name = "eric";
        String email = "eric@gmail.com";
        boolean isVip = false;
        CustomerRequestDto jsonBody = new CustomerRequestDto(name,email);

        CustomerResponseDto responseDto = new CustomerResponseDto(UUID.randomUUID(), name, email, isVip, List.of());

        Mockito.when(customerService.addCustomer(Mockito.any(CustomerRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_CUSTOMERS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Test get all customers with success")
    void findAllCustomersSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_CUSTOMERS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test to get a customer by his name")
    void findCustomerByNameSuccess() throws Exception {

        String name = "eric";
        mockMvc.perform(MockMvcRequestBuilders.get(API_CUSTOMERS_ENDPOINT + "/{name}", name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
