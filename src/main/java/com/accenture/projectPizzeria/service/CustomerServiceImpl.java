package com.accenture.projectPizzeria.service;

import com.accenture.projectPizzeria.exception.CustomerException;
import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import com.accenture.projectPizzeria.repository.model.Customer;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> customers = customerDao.findAll();
        return customers.stream()
                .map(customerMapper::toCustomerResponseDto)
                .toList();
    }

    @Override
    public CustomerResponseDto findByNameCustomer(String name) {
       Customer customer = null;
       try {
           customer = customerDao.findByName(name);
       }catch (EntityNotFoundException _){
           throw new EntityNotFoundException(messages.getMessage("Customer.name.notfound", name));
       }
       return customerMapper.toCustomerResponseDto(customer);
    }

    @Override
    public void verify(CustomerRequestDto requestDto) {
        if (requestDto == null)
            throw new CustomerException(messages.getMessage("customer.null"));
    }
}
