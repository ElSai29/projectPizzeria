package com.accenture.projectPizzeria.mapper;

import com.accenture.projectPizzeria.repository.model.Customer;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerRequestDto customerRequestDto);

    CustomerResponseDto toCustomerResponseDto(Customer customer);

}
