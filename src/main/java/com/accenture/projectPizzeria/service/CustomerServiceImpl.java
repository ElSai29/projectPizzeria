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

/**
 * All public methods are transaction. Read-Only transactions are explicitly
 * declared for read operations to improve performance
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;
    private MessageSourceAccessor messages;


    /**
     * Creates and persists a new customer.
     *
     * @param requestDto the customer creation request data
     * @return the created customer as a response DTO
     * @throws CustomerException if the provided request is null
     */
    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto requestDto) {
        log.info("Accessing method addCustomer");
        verify(requestDto);
        Customer saved = customerDao.save(customerMapper.toCustomer(requestDto));
        return customerMapper.toCustomerResponseDto(saved);
    }

    /**
     * Retrieves all customers stored in the database
     * This method executes within a read-only transaction and returns a list
     * of customers mapped to response DTOs.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> customers = customerDao.findAll();
        return customers.stream()
                .map(customerMapper::toCustomerResponseDto)
                .toList();
    }



    /**
     * Retrieves a customer by its name.
     * This method attempts to find a customer using the provided name.
     * If no customer is found, an {@link EntityNotFoundException} is thrown
     * with a localized error message.
     *
     * @param name the name of the customer to retrieve
     * @return the found customer as a response DTO
     * @throws EntityNotFoundException if no customer exists with the given name
     */

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

    /**
     * Verifies the validity of a customer request.
     * This method ensures that the provided CustomerRequestDto is not
     * null before any business operation is performed.
     * @param  requestDto customer request to validate
     * @throws CustomerException if the request DTO is null
     */
    @Override
    public void verify(CustomerRequestDto requestDto) {
        if (requestDto == null)
            throw new CustomerException(messages.getMessage("customer.null"));
    }
}
