package com.accenture.projectPizzeria.service;


import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import com.accenture.projectPizzeria.repository.model.Customer;
import com.accenture.projectPizzeria.service.dto.CustomerRequestDto;
import com.accenture.projectPizzeria.service.dto.CustomerResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.UUID;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplMockitoTest {


    @Mock
    private CustomerDao customerDao;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerService service;

    @Mock
    private MessageSourceAccessor messages;


    @BeforeEach
    void setUp() {
        customerDao = mock(CustomerDao.class);
        customerMapper = mock(CustomerMapper.class);
        messages = mock(MessageSourceAccessor.class);
        service = new CustomerServiceImpl(customerDao, customerMapper, messages);
    }

    @Test
    @DisplayName("Test when customer object is well persisted from valid input")
    void addCustomerValidInputOk(){
    CustomerService spy = Mockito.spy(service);
    String name = "jean";
    String email = "jean@gmail.com";
    boolean isVip = false;

    CustomerRequestDto customerRequestDto = new CustomerRequestDto(name,email);
    CustomerResponseDto returnedResponseDto = new CustomerResponseDto(UUID.randomUUID(), name, email, isVip);

    Customer customerEntity = new Customer(name, email, isVip);

    Mockito.when(customerMapper.toCustomer(Mockito.any(CustomerRequestDto.class))).thenReturn(customerEntity);
    Mockito.when(customerDao.save(Mockito.any(Customer.class))).thenReturn(customerEntity);
    Mockito.when(customerMapper.toCustomerResponseDto(Mockito.any(Customer.class))).thenReturn(returnedResponseDto);

    CustomerResponseDto returnedValue = spy.addCustomer(customerRequestDto);

        Assertions.assertAll(()->
                Assertions.assertNotNull(returnedValue, "DtoResponse should not be null"),
                () -> Assertions.assertNotNull(returnedValue.id(), "Id should not be null"),
                () -> Assertions.assertNotNull(returnedValue.name(), "name should not be null"),
                () -> Assertions.assertNotNull(returnedValue.email(), "email should not be null"),
                () -> Assertions.assertEquals(name, returnedValue.name(), "Name should be the same as the expected"),
                () -> Assertions.assertEquals(email, returnedValue.email(), "Name should be the same as the expected"));
        Mockito.verify(spy, Mockito.times(1)).verify(Mockito.any(CustomerRequestDto.class));
}

}