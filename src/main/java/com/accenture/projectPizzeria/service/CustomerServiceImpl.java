package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;
    private MessageSource messages;

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
        return null;
    }
}
