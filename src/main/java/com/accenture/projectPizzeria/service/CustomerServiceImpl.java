package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.exception.CustomerException;
import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import com.accenture.projectPizzeria.repository.model.Customer;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;
    private MessageSourceAccessor messages;

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto requestDto) {
        log.info("Accessing method addCustomer");
        verify(requestDto);
        Customer saved = customerDao.save(customerMapper.toCustomer(requestDto));
        return customerMapper.toCustomerResponseDto(saved);
    }

    @Override
    public void verify(CustomerRequestDto requestDto) {
        if (requestDto == null)
            throw new CustomerException(messages.getMessage("customer.null"));
    }
}
