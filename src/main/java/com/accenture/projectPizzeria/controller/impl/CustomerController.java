package com.accenture.projectPizzeria.controller.impl;

import com.accenture.projectPizzeria.controller.api.CustomerApi;
import com.accenture.projectPizzeria.service.CustomerService;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class CustomerController implements CustomerApi {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<Void> addCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        log.info("Accessing endpoint POST /{id}");
        CustomerResponseDto reponseDto = customerService.addCustomer(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        log.info("Accessing endpoint GET /allCustomers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @Override
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable String name) {
        log.info("Accessing endpoint GET /{name}");
        return ResponseEntity.ok(customerService.findByNameCustomer(name));
    }
}
