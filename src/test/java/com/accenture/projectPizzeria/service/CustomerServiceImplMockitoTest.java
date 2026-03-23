package com.accenture.projectPizzeria.service;


import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import com.accenture.projectPizzeria.repository.model.Customer;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplMockitoTest {


    @Mock
    private CustomerDao customerDao;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerService service;

    @Mock
    private MessageSource messages;


    @BeforeEach
    void setUp() {
        customerDao = mock(CustomerDao.class);
        customerMapper = mock(CustomerMapper.class);
        messages = mock(MessageSource.class);
        service = new CustomerServiceImpl(customerDao, customerMapper, messages);
    }


}
