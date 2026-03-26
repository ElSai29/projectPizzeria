package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto);

    void verify(CustomerRequestDto requestDto);

    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto findByNameCustomer(String name);
}
